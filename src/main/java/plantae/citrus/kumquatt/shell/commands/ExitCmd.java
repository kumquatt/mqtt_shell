package plantae.citrus.kumquatt.shell.commands;

import jline.console.completer.Completer;
import jline.console.completer.NullCompleter;
import plantae.citrus.kumquatt.shell.Environment;
import jline.console.ConsoleReader;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class ExitCmd implements Command {
    private String name;
    private Completer completer = new NullCompleter();

    private final String helpHeader = "exit the command shell.";

    public ExitCmd(String name){
        this.name = name;
    }

    public void execute(Environment env, CommandLine cmd, ConsoleReader reader) {
        System.exit(0);
    }

    public String getHelpHeader(){
        return helpHeader;
    }

    public String getUsage(){
        return this.getName();
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
} // end of class
