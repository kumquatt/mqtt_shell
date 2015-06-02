package plantae.citrus.kumquatt;

import jline.console.ConsoleReader;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.StringUtils;
import plantae.citrus.kumquatt.mqtt.MqttManager;
import plantae.citrus.kumquatt.shell.Environment;
import plantae.citrus.kumquatt.shell.ShellTemplate;
import plantae.citrus.kumquatt.shell.commands.CustomCmd;
import plantae.citrus.kumquatt.shell.commands.CustomCmdHandler;
import plantae.citrus.kumquatt.shell.commands.ExitCmd;
import plantae.citrus.kumquatt.shell.commands.HelpCmd;

import java.io.IOException;

public class MqttShellMain {


    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");

        System.out.println(MqttManager.getInstance().isConnected());

        CustomCmd connectCmd = new CustomCmd("connect", new CustomCmdHandler() {
            public void onEventReceived(Environment env, CommandLine cmd, ConsoleReader reader) throws Exception {
                String host = "broker.mqtt-dashboard.com";

                System.out.println(cmd.hasOption("c"));

                String clientId = cmd.getOptionValue("c","");
                System.out.println("!!!!! " + clientId);
                if (StringUtils.isNotEmpty(clientId)){
                    System.out.println("w/ clientId");
                    MqttManager.getInstance().connect(host, 1883, clientId);
                } else {
                    System.out.println("w/o clientId");
                    MqttManager.getInstance().connect(host, 1883);
                }
            }
        });

        connectCmd.addOption(new Option("c", "clientid", true, "client id name"));


        CustomCmd disconnectCmd = new CustomCmd("disconnect", new CustomCmdHandler() {
            public void onEventReceived(Environment env, CommandLine cmd, ConsoleReader reader) throws Exception {
                MqttManager.getInstance().disconnect();
            }
        });

        CustomCmd subscribeCmd = new CustomCmd("subscribe", new CustomCmdHandler() {
            public void onEventReceived(Environment env, CommandLine cmd, ConsoleReader reader) throws Exception {
                String topic = cmd.getOptionValue("topic");
                System.out.println("Subscribe " + topic);
                MqttManager.getInstance().subscribe(topic);
            }
        });

        Option topic = new Option("t", "topic", true, "topic name");
        topic.setRequired(true);
        Option message = new Option("m", "message", true, "message");
        message.setRequired(true);

        subscribeCmd.addOption(topic);

        CustomCmd publishCmd = new CustomCmd("publish", new CustomCmdHandler() {
            public void onEventReceived(Environment env, CommandLine cmd, ConsoleReader reader) throws Exception {
                String topic = cmd.getOptionValue("topic");
                String message = cmd.getOptionValue("message");
                MqttManager.getInstance().publish(topic, message);
            }
        });

        publishCmd.addOption(topic);
        publishCmd.addOption(message);

        CustomCmd receiveOneCmd = new CustomCmd("receive", new CustomCmdHandler() {
            public void onEventReceived(Environment env, CommandLine cmd, ConsoleReader reader) throws Exception {
                String topic = cmd.getOptionValue("topic");
                String message = cmd.getOptionValue("message");
                if (MqttManager.getInstance().receiveOne(topic, message)){
                    System.out.println("GOOD");
                } else {
                    System.out.println("NOGOOD");
                }
            }
        });
        receiveOneCmd.addOption(topic);
        receiveOneCmd.addOption(message);

        HelpCmd helpCmd = new HelpCmd("help");
        ExitCmd exitCmd = new ExitCmd("exit");

        try {
            ShellTemplate.custom().setShellName("hi")
                    .addCommand(connectCmd)
                    .addCommand(helpCmd)
                    .addCommand(exitCmd)
                    .addCommand(disconnectCmd)
                    .addCommand(subscribeCmd)
                    .addCommand(publishCmd)
                    .addCommand(receiveOneCmd)
                    .build().run();
        } catch (Exception e) {
            System.out.println("exception??? " + e.getMessage());
        }

    }

} // end of class
