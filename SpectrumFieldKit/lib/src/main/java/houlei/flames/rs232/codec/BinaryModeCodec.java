package houlei.flames.rs232.codec;

import houlei.flames.rs232.pack.RequestPack;
import houlei.flames.rs232.pack.ResponsePack;

public abstract class BinaryModeCodec implements PackCodec {

    @Override
    public byte[] encode(RequestPack pack) {
        byte[] buffer = encodeChar(pack.getKey(), new byte[1], 0);
//        encodeChar('\r', buffer, 1);
        return buffer;
    }

    @Override
    public abstract ResponsePack decode(byte[] buffer);

    protected static byte[] encodeChar(char ch, byte[] buffer, int offset) {
        buffer[offset] = (byte) ch;
        return buffer;
    }

    protected static byte[] encodeInt16(int value, byte[] buffer, int offset) {
        buffer[offset] = (byte)((value & 0x00FF00) >>> 8);
        buffer[offset+1] = (byte)(value & 0x00FF);
        return buffer;
    }

    protected static ResponsePack.Status decodeStatus(byte[] buffer, int offset) {
        for (ResponsePack.Status s : ResponsePack.Status.values()) {
            if ((char)buffer[offset] == s.getValue()) {
                return s;
            }
        }
        throw new IllegalArgumentException("[BinaryModeCodec][decodeStatus] buffer["+offset+"] 0x" + buffer[offset] + " is not a status data.");
    }

    protected static String decodeDigitString(byte[] buffer, int offset) {
        int length = 0;
        for (int i=offset; i<buffer.length; i++, length++) {
            if (!Character.isDigit(buffer[i])) {
                break;
            }
        }
        return new String(buffer, offset, length);
    }

    protected static int decodeInt16(byte[] buffer, int offset) {
        int value = (buffer[offset] & 0xFF) << 8;
        value |= buffer[offset+1] & 0xFF;
        return value;
    }

    protected static int decodeInt32(byte[] buffer, int offset) {
        int value = (buffer[offset] & 0xFF) << 24;
        value |= (buffer[offset+1] & 0xFF) << 16;
        value |= (buffer[offset+2] & 0xFF) << 8;
        value |= buffer[offset+3] & 0xFF;
        return value;
    }

    protected static String decodeString(byte[] buffer, int offset) {
        StringBuilder builder = new StringBuilder();
        for (int i=offset; i<buffer.length; i++) {
            if (buffer[i] == '\r' || buffer[i] == '\n') {
                break;
            }
            builder.append((char)buffer[i]);
        }
        return builder.toString();
    }
}
