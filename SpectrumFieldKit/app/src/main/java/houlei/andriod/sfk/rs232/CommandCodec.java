package houlei.andriod.sfk.rs232;

import java.nio.charset.StandardCharsets;

import houlei.andriod.sfk.rs232.cmd.ICommand;
import houlei.andriod.sfk.rs232.cmd.ReadCommand;
import houlei.andriod.sfk.rs232.cmd.SetCommand;

public class CommandCodec {

    public byte[] encode(ReadCommand cmd) {
        String name = cmd.getName();
        String[] parameters = cmd.getParameters();
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(ReadCommand.CMD_TYPE);
        if (parameters.length > 0) {
            builder.append(parameters[0]);
            if (parameters.length > 1) {
                for (int index=1; index<parameters.length; index++) {
                    builder.append(ReadCommand.SEPARATOR).append(parameters[index]);
                }
            }
        }
        builder.append(ICommand.TERMINATOR);
        return builder.toString().getBytes(StandardCharsets.US_ASCII);
    }

    public byte[] encode(SetCommand cmd) {
        String name = cmd.getName();
        String[] parameters = cmd.getParameters();
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(SetCommand.CMD_TYPE).append(parameters[0]);
        if (parameters.length>1) {
            for (int index=1; index<parameters.length;index++) {
                builder.append(SetCommand.SEPARATOR).append(parameters[index]);
            }
        }
        builder.append(ICommand.TERMINATOR);
        return builder.toString().getBytes(StandardCharsets.US_ASCII);
    }

    //TODO need test
    public ICommand decode(byte[] data) {
        String value = new String(data, StandardCharsets.US_ASCII);
        int index = value.indexOf(ReadCommand.CMD_TYPE);
        String type = null;
        String separator = null;
        ICommand command = null;
        if (index > 0) {
            type = ReadCommand.CMD_TYPE;
            separator = ReadCommand.SEPARATOR;
        } else if((index = value.indexOf(SetCommand.CMD_TYPE)) > 0) {
            type = SetCommand.CMD_TYPE;
            separator = SetCommand.SEPARATOR;
        } else {
            throw new IllegalArgumentException(("Illegal Data Format: need command type field."));
        }
        String name = value.substring(0, index);
        String parameter = value.substring(index+type.length(), value.lastIndexOf(ICommand.TERMINATOR));
//        StringTokenizer tokenizer = new StringTokenizer(parameter, separator);
//        String [] parameters = new String[tokenizer.countTokens()];
//        for (int i = 0; tokenizer.hasMoreTokens();) {
//            parameters[i++] = tokenizer.nextToken();
//        }
        String[] parameters = parameter.split(separator);
        if (ReadCommand.CMD_TYPE == type) {
            command = new ReadCommand(name, parameters);
        } else if (SetCommand.CMD_TYPE == type) {
            command = new SetCommand(name, parameters);
        }
        return command;
    }

}
