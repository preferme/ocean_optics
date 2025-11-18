package houlei.andriod.sfk.rs232.cmd.details;

import houlei.andriod.sfk.rs232.cmd.ICommand;
import houlei.andriod.sfk.rs232.cmd.ReadCommand;
import houlei.andriod.sfk.rs232.cmd.SetCommand;

public interface ScansToAverage {

    class Read extends ReadCommand {
        public Read() {
            super("A");
        }
    }

    class Set extends SetCommand {
        /**
         * Sets the number of discrete spectra to be summed together. Setting the scans to
         * average parameter above 1 scan will set the pixel size to 32 bits. See Spectra Data
         * Format section for details.
         * </br>
         * NOTE: Host software must divide 32 bits pixel value by the number of scans to
         * normalize each pixel value.
         */
        public Set(int number) {
            super("A",Integer.toString(number));
        }
    }

}
