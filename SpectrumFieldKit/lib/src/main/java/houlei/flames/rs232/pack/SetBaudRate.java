package houlei.flames.rs232.pack;

public interface SetBaudRate {

    enum BaudRate {
        BR2400(0), BR4800(1), BR9600(2),
        BR19200(3), BR38400(4), NotSupported(5),
        BR115200(6);
        private final int value;

        BaudRate(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    class Request extends RequestPack {
        private final BaudRate baudRate;

        public Request(BaudRate baudRate) {
            super('K');
            this.baudRate = baudRate == null ? BaudRate.BR9600 : baudRate;
        }

        public BaudRate getBaudRate() {
            return baudRate;
        }
    }

    class Response extends ResponsePack {
        public Response(Status status) {
            super(status);
        }
    }

}
