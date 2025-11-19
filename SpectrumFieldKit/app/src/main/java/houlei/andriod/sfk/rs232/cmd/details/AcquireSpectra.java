package houlei.andriod.sfk.rs232.cmd.details;

import houlei.andriod.sfk.rs232.cmd.ICommand;
import houlei.andriod.sfk.rs232.cmd.ReadCommand;

public interface AcquireSpectra {

    class Read extends ReadCommand {
        public Read() {
            super("S");
        }
    }

}
