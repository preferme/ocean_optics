package houlei.andriod.sfk.rs232.cmd.details;

import houlei.andriod.sfk.rs232.cmd.ICommand;
import houlei.andriod.sfk.rs232.cmd.ReadCommand;

public interface SerialNumber {

    class Read extends ReadCommand implements ICommand {
        public Read() {
            super("N");
        }
    }

}
