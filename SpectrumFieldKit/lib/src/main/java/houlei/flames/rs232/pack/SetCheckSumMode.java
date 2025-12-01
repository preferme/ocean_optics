package houlei.flames.rs232.pack;

public interface SetCheckSumMode {

    class Request extends RequestPack {
        private final boolean value;

        public Request(Boolean value) {
            super('k');
            this.value = value != null && value;
        }

        public boolean getValue() {
            return value;
        }

    }

    class Response extends ResponsePack {
        public Response(Status status) {
            super(status);
        }
    }
}
