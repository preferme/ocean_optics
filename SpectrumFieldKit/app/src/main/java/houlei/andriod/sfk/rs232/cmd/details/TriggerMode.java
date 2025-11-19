package houlei.andriod.sfk.rs232.cmd.details;

import houlei.andriod.sfk.rs232.cmd.ICommand;
import houlei.andriod.sfk.rs232.cmd.ReadCommand;
import houlei.andriod.sfk.rs232.cmd.SetCommand;

public interface TriggerMode {

    class Read extends ReadCommand {
        public Read() {
            super("T");
        }
    }

    class SetSoftware extends SetCommand {
        public SetSoftware() {
            super("T", "0");
        }
    }

    class SetExternalEdge extends SetCommand {
        public SetExternalEdge() {
            super("T", "1");
        }
    }

    class SetExternalLevel extends SetCommand {
        public SetExternalLevel() {
            super("T", "2");
        }
    }

}
