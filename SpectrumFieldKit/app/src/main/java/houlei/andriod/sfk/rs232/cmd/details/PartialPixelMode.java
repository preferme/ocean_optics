package houlei.andriod.sfk.rs232.cmd.details;

import houlei.andriod.sfk.rs232.cmd.ICommand;
import houlei.andriod.sfk.rs232.cmd.ReadCommand;

public interface PartialPixelMode {

    class Read extends ReadCommand {
        /**
         * Allows user to select which pixels are returned by an Acquire Spectra command. Note
         * that all pixels are still acquired for every scan, but this command will allow the user to
         * specify which ones are returned to the user. </p>
         * Two command arguments must be provided, the lower pixel first and the upper pixel.
         * Pixel values must be within a valid range. Refer to spectrometer user manual for the
         * number of available pixels.
         */
        public Read() {
            super("P");
        }
    }

}
