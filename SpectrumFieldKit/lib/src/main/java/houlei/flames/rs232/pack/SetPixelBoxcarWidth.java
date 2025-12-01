package houlei.flames.rs232.pack;

public interface SetPixelBoxcarWidth {

    class Request extends RequestPack {

        private final int value;

        public Request(Integer value) {
            super('B');
            this.value = value == null ? 0 : value;
            if (value < 0 || value > 15) {
                throw new IllegalArgumentException("Value is out of range[0,15].");
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
