package houlei.andriod.sfk.rs232.cmd;

import androidx.annotation.NonNull;

public class ReadCommand implements ICommand{

    public static final String CMD_TYPE = "?";
    public static final String SEPARATOR = ",";

    private static final String[] NONE_PARAMETERS = new String[0];

    protected final String name;
    protected final String[] parameters;

    public ReadCommand(String name, String... parameters) {
        this.name = name;
        this.parameters = parameters == null ? NONE_PARAMETERS : parameters;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getParameters() {
        return parameters;
    }

    public String getParameter() {
        if (parameters.length == 0) {
            return "";
        }
        if (parameters.length == 1) {
            return parameters[0];
        }
        StringBuilder builder = new StringBuilder(parameters[0]);
        for (int index=1; index<parameters.length;index++) {
            builder.append(SEPARATOR).append(parameters[index]);
        }
        return builder.toString();
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(CMD_TYPE);
        if (parameters.length > 0) {
            builder.append(parameters[0]);
            if (parameters.length > 1) {
                for (int index=1; index<parameters.length; index++) {
                    builder.append(SEPARATOR).append(parameters[index]);
                }
            }
        }
        builder.append(ICommand.TERMINATOR);
        return builder.toString();
    }

}
