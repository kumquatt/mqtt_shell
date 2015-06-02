package plantae.citrus.kumquatt.shell.commands;

import jline.console.ConsoleReader;
import org.apache.commons.cli.CommandLine;
import plantae.citrus.kumquatt.shell.Environment;

public interface CustomCmdHandler {
    void onEventReceived(Environment env, CommandLine cmd, ConsoleReader reader) throws Exception;
} // end of interface
