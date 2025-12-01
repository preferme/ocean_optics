package houlei.flames.rs232.pack;

import houlei.flames.rs232.codec.BinaryModeCodec;

public interface GetVersion {

    class Request extends RequestPack {
        public Request() {
            super('v');
        }
    }

    class Response extends ResponsePack {
        private final int version;
        private final boolean binaryMode;

        public Response(Status status, int version, boolean binaryMode) {
            super(status);
            this.version = version;
            this.binaryMode = binaryMode;
        }

        public int getVersion() {
            return version;
        }

        public boolean isBinaryMode() {
            return binaryMode;
        }

        @SuppressWarnings("DefaultLocale")
        @Override
        public String toString() {
            int major = version / 1000;
            int minor = (version - major * 1000) /10;
            int revision = version % 10;
            return "GetVersion.Response{" +
                    " status: " + status +
                    ", version: " + String.format("%d.%02d.%d", major, minor, revision) +
                    ", binaryMode: " + binaryMode +
                    '}';
        }
    }

    class BinaryCodec extends BinaryModeCodec {

        @Override
        public ResponsePack decode(byte[] buffer) {
            if (buffer[0] == ResponsePack.Status.ACK.getValue()) {
                return new Response(decodeStatus(buffer, 0), decodeInt16(buffer, 1), true);
            }
            if (buffer[0] == 'v') {
                return new Response(decodeStatus(buffer, 1), Integer.parseInt(decodeDigitString(buffer, 2)), false);
            }
            throw new IllegalArgumentException("[GetVersion][BinaryCodec][decode] buffer do not have response data");
        }
    }
}
