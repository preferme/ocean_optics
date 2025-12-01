package houlei.flames.rs232.pack;

public interface SetIntegrationTime {

    class Request extends RequestPack {
        private final int value;

        public Request(Integer value) {
            super('I');
            this.value = value == null ? 10 : value;
            if (value < 1 || value > 65000) {
                throw new IllegalArgumentException("Value is out of range[1,65000].");
            }
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
