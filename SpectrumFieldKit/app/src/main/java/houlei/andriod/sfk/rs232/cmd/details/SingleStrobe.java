package houlei.andriod.sfk.rs232.cmd.details;

import houlei.andriod.sfk.rs232.cmd.ICommand;
import houlei.andriod.sfk.rs232.cmd.ReadCommand;
import houlei.andriod.sfk.rs232.cmd.SetCommand;

public interface SingleStrobe {

    class Read extends ReadCommand {

        public Read() {
            super("B");
        }
    }

    class SetEnable extends SetCommand {
        /**
         * Configures the Single Strobe signal and associated parameters shown in Table 10. All
         * parameters are passed as a comma-separated list.
         * @param delay  Strobe Delay. Notes: Microseconds
         * @param width  Strobe Width. Notes: Microseconds
         */
        public SetEnable(int delay, int width) {
            super("B", "1", Integer.toString(delay), Integer.toString(width));
        }
    }

    class SetDisable extends SetCommand {
        public SetDisable() {
            super("B", "0");
        }
    }

}
