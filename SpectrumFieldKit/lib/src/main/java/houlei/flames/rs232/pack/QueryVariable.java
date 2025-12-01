package houlei.flames.rs232.pack;

import houlei.flames.rs232.codec.BinaryModeCodec;

public interface QueryVariable {

    class Request extends RequestPack {
        private final char value;

        public Request(char value) {
            super('?');
            this.value = value;
        }

        public char getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "QueryVariable.Request{ command: ?" + value
                    + '}';
        }
    }

    class Response extends ResponsePack {
        private final int value;
        public Response(Status status, int value) {
            super(status);
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "QueryVariable.Response{ status: " + status
                    + ", value: " + value
                    + '}';
        }
    }

    class BinaryCodec extends BinaryModeCodec {
        @Override
        public byte[] encode(RequestPack pack) {
            Request request = (Request) pack;
            char cmd = request.getValue();
            byte[] buffer = encodeChar(request.getKey(), new byte[2], 0);
            encodeChar(cmd, buffer, 1);
            return buffer;
        }

        @Override
        public ResponsePack decode(byte[] buffer) {
            ResponsePack.Status status = decodeStatus(buffer, 0);
            int value = status == ResponsePack.Status.ACK
                    ? decodeInt16(buffer, 1)
                    : 0;
            return new Response(status, value);
        }
    }


}
