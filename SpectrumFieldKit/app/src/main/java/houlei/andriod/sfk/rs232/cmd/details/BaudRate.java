package houlei.andriod.sfk.rs232.cmd.details;

import houlei.andriod.sfk.rs232.cmd.ICommand;
import houlei.andriod.sfk.rs232.cmd.SetCommand;

public interface BaudRate {

    class Set extends SetCommand {
        /**
         * When changing baud rates, the following sequence must be followed:
         * 1. Host sends desired baud rate value, communicating at the current baud rate.
         * 2. Device acknowledges request with OK, otherwise it responds with ERROR.
         * 3. Host waits longer than 50 ms and switches to new baud rate.
         * 4. Host resends the command baud rate value, communicating at the new baud rate.
         * 5. Device confirms change, communicating at the new baud rate.
         * NOTE: If a deviation occurs at any step, the previous baud rate is utilized
         * @param baudRate Notes: 2400, 9600, 14400,19200, 38400, 115200
         */
        public Set(int baudRate) {
            super("K", Integer.toString(baudRate));
        }

        public enum BaudRates {
            BAUD_RATES_2400(2400),
            BAUD_RATES_9600(9600),
            BAUD_RATES_14400(14400),
            BAUD_RATES_19200(19200),
            BAUD_RATES_38400(38400),
            BAUD_RATES_115200(115200);

            private final int value;
            BaudRates(int value) {
                this.value = value;
            }
        }
        public Set(BaudRates baudRates) {
            super("K", Integer.toString(baudRates.value));
        }
    }
}
