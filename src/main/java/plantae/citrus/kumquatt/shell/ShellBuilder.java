package plantae.citrus.kumquatt.shell;

import plantae.citrus.kumquatt.shell.commands.Command;

import java.util.LinkedHashSet;
import java.util.Set;

public class ShellBuilder {
    private String shellName = "temp";
    private Set<Command> commandSet = new LinkedHashSet<Command>();

    public static ShellBuilder create() {
        return new ShellBuilder();
    }

    public Shell build(){
        return new Shell(shellName, commandSet);
    }

    public ShellBuilder setShellName(String name){
        this.shellName = name;
        return this;
    }

    public String getShellName(){
        return shellName;
    }

    public ShellBuilder addCommand(Command command){
        commandSet.add(command);
        return this;
    }

    public ShellBuilder addCommands(Set<Command> commands){
        commandSet.addAll(commands);
        return this;
    }

    public boolean containsCommand(Command command){
        return commandSet.contains(command);
    }

} // end of class
