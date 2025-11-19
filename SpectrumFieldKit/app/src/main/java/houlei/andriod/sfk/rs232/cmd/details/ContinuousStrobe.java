package houlei.andriod.sfk.rs232.cmd.details;

import houlei.andriod.sfk.rs232.cmd.ICommand;
import houlei.andriod.sfk.rs232.cmd.ReadCommand;
import houlei.andriod.sfk.rs232.cmd.SetCommand;

public interface ContinuousStrobe {

    class Read extends ReadCommand {
        public Read() {
            super("C");
        }
    }

    class SetEnable extends SetCommand {
        /**
         * Configures the Continuous Strobe signal.
         * All parameters are passed as a comma-separated list.
         * @param period Strobe Period.  Notes: Microseconds
         */
        public SetEnable(int period) {
            super("C", "1", Integer.toString(period));
        }
    }

    class SetDisable extends SetCommand {
        public SetDisable() {
            super("C", "0");
        }
    }

}
