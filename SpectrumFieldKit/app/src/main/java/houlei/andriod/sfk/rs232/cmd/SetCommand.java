package houlei.andriod.sfk.rs232.cmd;

import androidx.annotation.NonNull;


public class SetCommand implements ICommand{

    public static final String CMD_TYPE = "=";
    public static final String SEPARATOR = ",";

    protected final String name;
    protected final String[] parameters;

    public SetCommand(String name, String... parameters) {
        this.name = name;
        if (parameters == null) {
            throw new IllegalArgumentException("Command [" + name + "] must have parameter(s).");
        }
        this.parameters = parameters;
    }

    public SetCommand(String name, String parameter) {
        this.name = name;
        this.parameters = new String[]{parameter};
    }

    public String getName() {
        return name;
    }

    public String[] getParameters() {
        return parameters;
    }

    public String getParameter() {
        if (parameters.length==1) {
            return parameters[0];
        }
        if (parameters.length>1) {
            StringBuilder builder = new StringBuilder(parameters[0]);
            for (int index=1; index<parameters.length;index++) {
                builder.append(SEPARATOR).append(parameters[index]);
            }
            return builder.toString();
        }
        throw new IllegalStateException("Command [" + name + "] must have parameter(s).");
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(CMD_TYPE).append(parameters[0]);
        if (parameters.length>1) {
            for (int index=1; index<parameters.length;index++) {
                builder.append(SEPARATOR).append(parameters[index]);
            }
        }
        builder.append(ICommand.TERMINATOR);
        return builder.toString();
    }


}
