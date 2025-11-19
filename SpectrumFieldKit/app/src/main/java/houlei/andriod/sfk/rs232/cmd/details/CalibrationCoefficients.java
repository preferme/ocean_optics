package houlei.andriod.sfk.rs232.cmd.details;

import houlei.andriod.sfk.rs232.cmd.ICommand;
import houlei.andriod.sfk.rs232.cmd.ReadCommand;

public interface CalibrationCoefficients {

    class Read extends ReadCommand {
        public Read(int index) {
            super("X", Integer.toString(index));
        }
    }
}
