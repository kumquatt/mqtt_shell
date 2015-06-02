package plantae.citrus.kumquatt;

import jline.console.ConsoleReader;
import org.apache.commons.cli.CommandLine;
import plantae.citrus.kumquatt.mqtt.ConnectionManager;
import plantae.citrus.kumquatt.shell.ShellTemplate;
import plantae.citrus.kumquatt.shell.commands.CustomCmd;
import plantae.citrus.kumquatt.shell.commands.CustomCmdHandler;
import plantae.citrus.kumquatt.shell.commands.ExitCmd;
import plantae.citrus.kumquatt.shell.commands.HelpCmd;

import java.io.IOException;

public class MqttShellMain {


    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");

        System.out.println(ConnectionManager.getInstance().isConnected());

        CustomCmd connectCmd = new CustomCmd("connect", new CustomCmdHandler() {
            public void onEventReceived(CommandLine cmd, ConsoleReader reader) throws Exception {
                ConnectionManager.getInstance().connect("localhost", 1883);
            }
        });
        HelpCmd helpCmd = new HelpCmd("help");
        ExitCmd exitCmd = new ExitCmd("exit");


        try {
            ShellTemplate.custom().setShellName("hi").addCommand(connectCmd).addCommand(helpCmd).addCommand(exitCmd).build().run();
        } catch (Exception e) {
            System.out.println("exception??? " + e.getMessage());
        }

    }

} // end of class
