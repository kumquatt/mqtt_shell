package plantae.citrus.kumquatt.shell;

import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;
import jline.console.history.FileHistory;
import jline.console.history.History;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import plantae.citrus.kumquatt.shell.commands.Command;
import plantae.citrus.kumquatt.shell.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class Shell {
    private static CommandLineParser parser = new PosixParser();
    private Environment env = new Environment();
    private final String name;
    private final Set<Command> commands;

    public Shell(String name, Set<Command> commands) {
        this.name = name;
        this.commands = commands;
    }


    public void run() throws Exception {
        initialize(env);

        if (StringUtils.isEmpty(env.getPrompt())) {
            env.setPrompt(getName() + "$");
        }

        ConsoleReader reader = new ConsoleReader();
        reader.addCompleter(initCompleters(env));
        reader.setHistory(initHistory());

        String line;

        while ((line = reader.readLine(env.getPrompt() + " ")) != null) {
            String[] arguments = line.split("\\s");
            String commandName = arguments[0];

            Command command = env.getCommand(commandName);
            if (command != null) {
                ////
                System.out.println("Running: " + command.getName() + " (" + command.getClass().getName() + ")");
                String[] cmdArgs = Arrays.copyOfRange(arguments, 1, arguments.length);
                CommandLine commandLine = parse(command, cmdArgs);

                if (commandLine != null) {
                    try {
                        command.execute(env, commandLine, reader);
                    } catch (Throwable e) {
                        System.out.println("Command failed with error: " + e.getMessage());

                        Logger.logv(commandLine, e.getMessage());
                    }
                }

            }
        }
    }

    private CommandLine parse(Command cmd, String[] args) {
        Options opts = cmd.getOptions();
        CommandLine ret = null;

        try {
            ret = parser.parse(opts, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;
    }

    private Completer initCompleters(Environment env) {
        ArrayList<Completer> completers = new ArrayList<Completer>();
        for (String cmdName : env.commandList()) {
            StringsCompleter sc = new StringsCompleter(cmdName);
            ArrayList<Completer> cmdCompleters = new ArrayList<Completer>();
            cmdCompleters.add(sc);
            cmdCompleters.add(env.getCommand(cmdName).getCompleter());

            ArgumentCompleter ac = new ArgumentCompleter(cmdCompleters);
            completers.add(ac);
        }

        AggregateCompleter aggComp = new AggregateCompleter(completers);

        return aggComp;
    }

    private History initHistory() throws IOException {
        File dir = new File(System.getProperty("user.home"), "."
                + this.getName());

        if (dir.exists() && dir.isFile()) {
            throw new IllegalStateException("Default configuration file exists and is not a directory: " + dir.getAbsolutePath());
        } else if (!dir.exists()) {
            dir.mkdir();
        }

        File histFile = new File(dir, "history");
        if (!histFile.exists()) {
            if (!histFile.createNewFile()) {
                throw new IllegalStateException("Unable to create history file: " + histFile.getAbsolutePath());
            }
        }

        final FileHistory hist = new FileHistory(histFile);
        hist.setMaxSize(50);
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                try {
                    hist.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return hist;
    }

    public void initialize(Environment env) throws Exception {
        for (Command cmd : commands) {
            env.addCommand(cmd);
        }
    }

    public String getName() {
        return this.name;
    }

} // end of class
