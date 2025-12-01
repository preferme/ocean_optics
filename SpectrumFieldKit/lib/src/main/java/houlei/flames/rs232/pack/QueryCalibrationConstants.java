package houlei.flames.rs232.pack;

import houlei.flames.rs232.codec.BinaryModeCodec;

public interface QueryCalibrationConstants {

    class Request extends RequestPack {
        private final char command = 'x';
        private final int constants;

        public Request(int constants) {
            super('?');
            this.constants = constants;
        }

        public char getCommand() {
            return command;
        }

        public int getConstants() {
            return constants;
        }

        @Override
        public String toString() {
            return "QueryVariable.XRequest{ command: ?" + command
                    + constants
                    + '}';
        }

    }

    class Response extends ResponsePack {
        private final String value;
        public Response(Status status, String value) {
            super(status);
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "QueryVariable.Response{ status: " + status
                    + ", value: " + (value == null ? "null" : "\"" + value + "\"")
                    + '}';
        }
    }

    class BinaryCodec extends BinaryModeCodec {
        @Override
        public byte[] encode(RequestPack pack) {
            Request request = (Request) pack;
            byte[] buffer = encodeChar(request.getKey(), new byte[4], 0);
            encodeChar(request.getCommand(), buffer, 1);
            encodeInt16(request.getConstants(), buffer, 2);
            return buffer;
        }

        @Override
        public ResponsePack decode(byte[] buffer) {
            ResponsePack.Status status = decodeStatus(buffer, 0);
            String value = status == ResponsePack.Status.ACK
                    ? decodeString(buffer, 1)
                    : null;
            return new Response(status, value);
        }
    }

}
