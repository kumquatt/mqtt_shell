package plantae.citrus.kumquatt.shell.commands;

import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.Completer;
import jline.console.completer.NullCompleter;
import jline.console.completer.StringsCompleter;
import plantae.citrus.kumquatt.shell.Environment;
import plantae.citrus.kumquatt.shell.utils.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class HelpCmd implements Command {
    private String name;
    private Completer completer = new NullCompleter();

    private Environment env;

    public HelpCmd(String name, Environment env) {
        this.name = name;
        this.env = env;

        this.completer = new AggregateCompleter(
                new StringsCompleter(this.env.commandList()),
                new NullCompleter());
    }

    public String getHelpHeader() {
        return "Options:";
    }

    public String getUsage() {
        return getName() + " [OPTION ...][ARGS ...]";
    }

    public String getName() {
        return name;
    }

    public Options getOptions() {
        Options opts = new Options();
        opts.addOption("v", "verbose", false, "show verbose output");
        return opts;
    }

    public Completer getCompleter() {
        return completer;
    }

    public void execute(Environment env, CommandLine cmd, ConsoleReader reader) {
        if (cmd.getArgs().length == 0) {
            for (String str : env.commandList()) {
                Logger.log(cmd, str);
            }
        } else {
            Command command = env.getCommand(cmd.getArgs()[0]);
            Logger.logv(cmd, "Get Help for command: " + command.getName() + "(" + command.getClass().getName() + ")");
            printHelp(command);
        }
    }

    private void printHelp(Command cmd) {
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp(cmd.getUsage(), cmd.getHelpHeader(), cmd.getOptions(), "");
    }
}
