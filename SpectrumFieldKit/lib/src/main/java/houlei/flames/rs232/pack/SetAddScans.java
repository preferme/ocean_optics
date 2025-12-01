package houlei.flames.rs232.pack;

import houlei.flames.rs232.codec.BinaryModeCodec;

public interface SetAddScans {

    class Request extends RequestPack {
        private final int value;

        public Request(Integer value) {
            super('A');
            this.value = value == null ? 1 : value;
            if (this.value < 1 || this.value > 5000) {
                throw new IllegalArgumentException("Value is out of range [1,5000].");
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

    class BinaryCodec extends BinaryModeCodec {

        @Override
        public byte[] encode(RequestPack pack) {
            Request request = (Request) pack;
            byte[] buffer = encodeChar(request.getKey(), new byte[3], 0);
            encodeInt16(request.getValue(), buffer, 1);
            return buffer;
        }

        @Override
        public ResponsePack decode(byte[] buffer) {
            return new Response(decodeStatus(buffer, 0));
        }
    }
}
