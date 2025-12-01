package houlei.flames.rs232.pack;

@Deprecated
public interface SetIntegrationTime32 {

    class Request extends RequestPack {
        private final int value;

        public Request(Integer value) {
            super('i');
            this.value = value == null ? 100000 : value;
            if (value < 1000 || value > 65000000) {
                throw new IllegalArgumentException("Value is out of range[1000, 65`000`000].");
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
