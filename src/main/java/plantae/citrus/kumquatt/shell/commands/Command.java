package plantae.citrus.kumquatt.shell.commands;

import plantae.citrus.kumquatt.shell.Environment;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public interface Command {
    String getHelpHeader();

    String getUsage();

    String getName();

    Options getOptions();

    Completer getCompleter();

    void execute(Environment env, CommandLine cmd, ConsoleReader reader);

} // end of class
