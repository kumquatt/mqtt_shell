package plantae.citrus.kumquatt.shell.utils;

import org.apache.commons.cli.CommandLine;

import java.io.PrintWriter;

public class Logger {
    private static PrintWriter out = new PrintWriter(System.out);
    public static void log(CommandLine cmd, String log){
        out.println(log);
        out.flush();
    }

    public static void logv(CommandLine cmd, String log){
        if (cmd.hasOption("v")){
            Logger.log(cmd, log);
        }
    }
} // end of class
