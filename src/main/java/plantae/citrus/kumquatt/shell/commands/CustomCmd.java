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
    private CustomCmdHandler handler;
    private String helpHeader = "Custom Command:";
    private String usage = "Custom Command usage?????";


    public CustomCmd(String name, CustomCmdHandler handler) {
        this.name = name;
        this.handler = handler;
    }

    public String getHelpHeader() {
        return helpHeader;
    }

    public void setHelpHeader(String helpHeader) {
        this.helpHeader = helpHeader;
    }

    public String getUsage() {
        return getName() + ":" + usage;
    }

    public void setUsage(String usage){
        this.usage = usage;
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
                handler.onEventReceived(cmd, reader);
            } catch (Exception ex){
                Logger.log(cmd, "....");
                Logger.log(cmd, ex.getMessage());
                Logger.log(cmd, getUsage());
                Logger.log(cmd, "....");
            }
        }
    }
} // end of class
