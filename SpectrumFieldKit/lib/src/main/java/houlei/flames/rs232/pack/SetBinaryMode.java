package houlei.flames.rs232.pack;

public interface SetBinaryMode {

    class Request extends RequestPack {
        private final char separator;

        public Request() {
            super('b');
            this.separator = 'B';
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
