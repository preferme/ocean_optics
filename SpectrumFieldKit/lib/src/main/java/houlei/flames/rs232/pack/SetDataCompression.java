package houlei.flames.rs232.pack;

public interface SetDataCompression {

    class Request extends RequestPack {

        public enum Compression{
            ON, OFF
        }

        private final Compression compression;

        public Request(Compression compression) {
            super('G');
            this.compression = compression == null ? Compression.OFF : compression;
        }

        public Compression getCompression() {
            return compression;
        }
    }

    class Response extends ResponsePack {

        public Response(Status status) {
            super(status);
        }
    }

}
