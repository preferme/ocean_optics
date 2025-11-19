package houlei.andriod.sfk.rs232.cmd;

import java.nio.charset.StandardCharsets;


public interface ICommand {

    String TERMINATOR = "\r";

    String getName();

    String[] getParameters();
}
