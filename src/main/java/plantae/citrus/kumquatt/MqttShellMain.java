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

                String clientId = cmd.getOptionValue("c","");
                if (StringUtils.isNotEmpty(clientId)){
                    System.out.println("w/ clientId");
                    MqttManager.getInstance().connect(host, 1883, clientId);
                } else {
                    System.out.println("w/o clientId");
                    MqttManager.getInstance().connect(host, 1883);
                }
            }
        });

        connectCmd.addOption(new Option("c", "clientid", false, "client id name"));


        CustomCmd disconnectCmd = new CustomCmd("disconnect", new CustomCmdHandler() {
            public void onEventReceived(Environment env, CommandLine cmd, ConsoleReader reader) throws Exception {
                MqttManager.getInstance().disconnect();
            }
        });

        HelpCmd helpCmd = new HelpCmd("help");
        ExitCmd exitCmd = new ExitCmd("exit");

        try {
            ShellTemplate.custom().setShellName("hi").addCommand(connectCmd).addCommand(helpCmd).addCommand(exitCmd).addCommand(disconnectCmd).build().run();
        } catch (Exception e) {
            System.out.println("exception??? " + e.getMessage());
        }

    }

} // end of class
