package houlei.andriod.sfk.rs232.cmd.details;

import houlei.andriod.sfk.rs232.cmd.ICommand;
import houlei.andriod.sfk.rs232.cmd.ReadCommand;
import houlei.andriod.sfk.rs232.cmd.SetCommand;

public interface IntegrationTime {

    class Read extends ReadCommand {
        public Read() {
            super("I");
        }
    }

    class Set extends SetCommand {
        /**
         * Refer to spectrometer user manual for minimum and maximum integration times.
         * Integration time value is specified in microseconds.
         * @param integrationTime Notes: Microsecond
         */
        public Set(int integrationTime) {
            super("I", Integer.toString(integrationTime));
        }
    }

}
