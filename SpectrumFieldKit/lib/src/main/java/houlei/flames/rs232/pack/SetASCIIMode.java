package houlei.flames.rs232.pack;

public interface SetASCIIMode {

    class Request extends RequestPack {
        private final char separator;
        public Request() {
            super('a');
            separator = 'A';
        }

        public char getSeparator() {
            return separator;
        }
    }

    class Response extends ResponsePack {
        public Response(Status status) {
            super(status);
        }
    }

}
