package plantae.citrus.kumquatt.shell.commands;

import jline.console.ConsoleReader;
import org.apache.commons.cli.CommandLine;

public interface CustomCmdHandler {
    void onEventReceived(CommandLine cmd, ConsoleReader reader) throws Exception;
} // end of interface
