package plantae.citrus.kumquatt.shell.commands;

import jline.console.completer.Completer;
import jline.console.completer.NullCompleter;
import plantae.citrus.kumquatt.shell.Environment;
import jline.console.ConsoleReader;
import plantae.citrus.kumquatt.shell.utils.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.Properties;

public class EnvCmd implements Command {
    private String name;
    private Completer completer = new NullCompleter();
    private final String systemOptionStr = "list system properties.";
    private final String localOptionStr = "list local properties.";
    public EnvCmd(String name){
        this.name = name;
    }

    public void execute(Environment env, CommandLine cmd, ConsoleReader reader) {
        if (cmd.hasOption("l") || !cmd.hasOption("s")) {
            Properties props = env.getProperties();
            Logger.log(cmd, "Local Properties:");
            for (Object key : props.keySet()){
                Logger.log(cmd, "\t" + key + "=" + props.get(key));
            }
        }

        if (cmd.hasOption("s")) {
            Logger.log(cmd, "System Properties:");
            Properties props = System.getProperties();
            for (Object key : props.keySet()){
                Logger.log(cmd, "\t" + key + "=" + props.get(key));
            }
        }
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

    public Options getOptions(){
        Options opts = new Options();
        opts.addOption("v", "verbose", false, "show verbose output");
        opts.addOption("s", "system", false, systemOptionStr);
        opts.addOption("l", "local", false, localOptionStr);

        return opts;
    }

    public Completer getCompleter() {
        return this.completer;
    }
} // end of class
