package plantae.citrus.kumquatt.shell.commands;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.completer.NullCompleter;
import plantae.citrus.kumquatt.shell.Environment;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import plantae.citrus.kumquatt.shell.utils.Logger;

public class CustomCmd implements Command {
    private String name;
    private Completer completer = new NullCompleter();

    public interface ActorHandler {
        void onEventReveived(CommandLine cmd, ConsoleReader reader) throws Exception;
    }

    private ActorHandler handler;

    public CustomCmd(String name, ActorHandler handler) {
        this.name = name;
        this.handler = handler;
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
        return this.completer;
    }

    public void execute(Environment env, CommandLine cmd, ConsoleReader reader) {
        if (handler != null) {
            try {
                handler.onEventReveived(cmd, reader);
            } catch (Exception ex){
                Logger.log(cmd, "....");
                Logger.log(cmd, ex.getMessage());
                Logger.log(cmd, getUsage());
                Logger.log(cmd, "....");
            }
        }
    }
} // end of class
