package houlei.andriod.sfk.rs232.cmd.details;

import houlei.andriod.sfk.rs232.cmd.ICommand;
import houlei.andriod.sfk.rs232.cmd.ReadCommand;
import houlei.andriod.sfk.rs232.cmd.SetCommand;

public interface LampEnableFeature {

    class Read extends ReadCommand {
        public Read() {
            super("J");
        }
    }

    class SetHigh extends SetCommand {
        public SetHigh() {
            super("J", "1");
        }
    }

    class SetLow extends SetCommand {
        public SetLow() {
            super("J", "0");
        }
    }

}
