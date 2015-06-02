package plantae.citrus.kumquatt.shell;

import plantae.citrus.kumquatt.shell.commands.Command;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class Environment {
    private String prompt = "";
    private Properties props = new Properties();
    private HashMap<String, Object> values = new HashMap<String, Object>();

    public Environment(){
    }

    private HashMap<String, Command> commands = new HashMap<String, Command>();

    public void addCommand(Command cmd){
        this.commands.put(cmd.getName(), cmd);
    }

    public Command getCommand(String name){
        return this.commands.get(name);
    }

    public Set<String> commandList(){
        return this.commands.keySet();
    }

    public void setProperty(String key, String value){
        if(value == null){
            this.props.remove(key);
        } else{
            this.props.setProperty(key, value);
        }
    }

    public String getProperty(String key){
        return this.props.getProperty(key);
    }

    public Properties getProperties(){
        return this.props;
    }

    public void setValue(String key, Object value){
        this.values.put(key, value);
    }

    public Object getValue(String key){
        return this.values.get(key);
    }


    public String getPrompt(){
        return this.prompt;
    }

    public void setPrompt(String prompt){
        this.prompt = prompt;
    }
} // end of class
