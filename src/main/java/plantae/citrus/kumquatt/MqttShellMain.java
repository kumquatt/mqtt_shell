package plantae.citrus.kumquatt;

import jline.console.ConsoleReader;
import org.apache.commons.cli.CommandLine;
import plantae.citrus.kumquatt.mqtt.ConnectionManager;
import plantae.citrus.kumquatt.shell.ShellTemplate;
import plantae.citrus.kumquatt.shell.commands.CustomCmd;
import plantae.citrus.kumquatt.shell.commands.ExitCmd;

import java.io.IOException;

public class MqttShellMain {


    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");

        System.out.println(ConnectionManager.getInstance().isConnected());

        CustomCmd connectCmd = new CustomCmd("connect", new CustomCmd.ActorHandler() {
            public void onEventReveived(CommandLine cmd, ConsoleReader reader) throws Exception {
                ConnectionManager.getInstance().connect("localhost", 1883);
            }
        });


        try {
            ShellTemplate.custom().setShellName("hi").addCommand(new ExitCmd("exit")).addCommand(connectCmd).build().run();
        } catch (Exception e) {
            System.out.println("exception??? " + e.getMessage());
        }

    }

} // end of class
