package houlei.flames.rs232.pack;

public interface SetFpgaRegister {

    class Request extends RequestPack {
        private final int address;
        private final int value;

        public Request(int address, int value) {
            super('W');
            this.address = address;
            this.value = value;
        }

        public int getAddress() {
            return address;
        }

        public int getValue() {
            return value;
        }

    }

    class Response extends ResponsePack {
        public Response(Status status) {
            super(status);
        }
    }

}
