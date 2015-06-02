package plantae.citrus.kumquatt.shell.commands;

import jline.console.completer.Completer;
import jline.console.completer.NullCompleter;
import plantae.citrus.kumquatt.shell.Environment;
import jline.console.ConsoleReader;
import jline.console.history.History;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.ListIterator;

public class HistoryCmd implements Command{

    private String name;
    private Completer completer = new NullCompleter();

    public HistoryCmd(String name){
        this.name = name;
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
        History history = reader.getHistory();
        ListIterator<History.Entry> it = history.entries();
        while(it.hasNext()){
            History.Entry entry = it.next();
            System.out.println(entry.value());
        }
    }
} // end of class
