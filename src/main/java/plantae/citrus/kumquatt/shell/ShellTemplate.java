package plantae.citrus.kumquatt.shell;

public class ShellTemplate {

    public static ShellBuilder custom() {
        return ShellBuilder.create();
    }

    public static Shell createDefault(){
        return ShellBuilder.create().build();
    }

} // end of class
