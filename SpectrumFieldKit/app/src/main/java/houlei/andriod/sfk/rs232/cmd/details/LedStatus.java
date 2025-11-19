package houlei.andriod.sfk.rs232.cmd.details;

import houlei.andriod.sfk.rs232.cmd.ICommand;
import houlei.andriod.sfk.rs232.cmd.ReadCommand;
import houlei.andriod.sfk.rs232.cmd.SetCommand;

public interface LedStatus {

    class Read extends ReadCommand {
        public Read() {
            super("L");
        }
    }

    class SetEnable extends SetCommand {
        public SetEnable() {
            super("L", "1");
        }
    }

    class SetDisable extends SetCommand {
        public SetDisable() {
            super("L", "0");
        }
    }

}
