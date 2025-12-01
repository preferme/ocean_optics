package houlei.flames.rs232.pack;

import java.util.Arrays;

import houlei.flames.rs232.codec.BinaryModeCodec;

public interface GetSpectralAcquisition {

    class Request extends RequestPack {

        public Request() {
            super('S');
        }
    }

    enum DataSize {
        WORDs(0), DWORDs(1);
        private final int value;

        DataSize(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return name() + "(" + value + ")";
        }
    }

    enum PixelMode {
        ALL(0), N_NO_AVE(1), N_A(2), X_Y_EVERY_N(3), RAND(4);
        private final int value;

        PixelMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return name() + "(" + value + ")";
        }
    }

    class Response extends ResponsePack {
        private final DataSize dataSize;
        private final int scansAccumulated;
        private final int integrationTIme;
        private final int fpgaRegisterAddress;
        private final int fpgaRegisterValue;
        private final PixelMode pixelMode;
        private final int[] pixelModeParams;
        private final int[] spectralData;

        public Response(Status status, DataSize dataSize, int scansAccumulated, int integrationTIme, int fpgaRegisterAddress, int fpgaRegisterValue, PixelMode pixelMode, int[] pixelModeParams, int[] spectralData) {
            super(status);
            this.dataSize = dataSize;
            this.scansAccumulated = scansAccumulated;
            this.integrationTIme = integrationTIme;
            this.fpgaRegisterAddress = fpgaRegisterAddress;
            this.fpgaRegisterValue = fpgaRegisterValue;
            this.pixelMode = pixelMode;
            this.pixelModeParams = pixelModeParams;
            this.spectralData = spectralData;
        }

        public DataSize getDataSize() {
            return dataSize;
        }

        public int getScansAccumulated() {
            return scansAccumulated;
        }

        public int getIntegrationTIme() {
            return integrationTIme;
        }

        public int getFpgaRegisterAddress() {
            return fpgaRegisterAddress;
        }

        public int getFpgaRegisterValue() {
            return fpgaRegisterValue;
        }

        public PixelMode getPixelMode() {
            return pixelMode;
        }

        public int[] getPixelModeParams() {
            return pixelModeParams;
        }

        public int[] getSpectralData() {
            return spectralData;
        }

        @Override
        public String toString() {
            if (status == Status.STX) {
                return "GetSpectralAcquisition.Response{" +
                        " status: " + status +
                        ", dataSize: " + dataSize +
                        ", scansAccumulated: " + scansAccumulated +
                        ", integrationTIme: " + integrationTIme +
                        ", fpgaRegisterAddress: " + fpgaRegisterAddress +
                        ", fpgaRegisterValue: " + fpgaRegisterValue +
                        ", pixelMode: " + pixelMode +
                        ", pixelModeParams: " + Arrays.toString(pixelModeParams) +
                        ", spectralData.length: " + spectralData.length +
                        '}';
            }
            return "GetSpectralAcquisition.Response{" +
                    " status: " + status +
                    '}';
        }
    }

    class BinaryCodec extends BinaryModeCodec {
        protected static DataSize decodeDataSize(byte[] buffer, int offset) {
            for (DataSize ds : DataSize.values()) {
                if ((char)buffer[offset] == ds.getValue()) {
                    return ds;
                }
            }
            throw new IllegalArgumentException("[GetSpectralAcquisition][BinaryCodec][decodeDataSize] buffer["+offset+"] 0x" + buffer[offset] + " is not data size.");
        }

        protected static PixelMode decodePixelMode(byte[] buffer, int offset) {
            for (PixelMode pm : PixelMode.values()) {
                if ((char)buffer[offset] == pm.getValue()) {
                    return pm;
                }
            }
            throw new IllegalArgumentException("[GetSpectralAcquisition][BinaryCodec][decodePixelMode] buffer["+offset+"] 0x" + buffer[offset] + " is not pixel mode.");
        }

        // TODO 测试高速情况下的数据解析以及4字节数据的解析
        @Override
        public ResponsePack decode(byte[] buffer) {
            ResponsePack.Status status = decodeStatus(buffer, 0);
            if (status == ResponsePack.Status.ETX) {
                return new Response(status, null,0,0,0,0,null,null,null);
            }
            if ((buffer[1] & 0xFF) != 0xFF || (buffer[2] & 0xFF) != 0xFF) {
                throw new IllegalArgumentException("[GetSpectralAcquisition][BinaryCodec][decode] 频谱帧起始符错误");
            }
            DataSize dataSize = decodeDataSize(buffer, 3);
            int scansAccumulated = decodeInt16(buffer, 5);
            int integrationTimeMS = decodeInt16(buffer, 7);
            int fpgaRegisterAddress = decodeInt16(buffer, 9);
            int fpgaRegisterValue = decodeInt16(buffer, 11);
            PixelMode pixelMode = decodePixelMode(buffer, 13);
            int[] pixelModeParams = null;
            switch (pixelMode) {
                case ALL: case N_A:
                    pixelModeParams = new int[0];
                    break;
                case N_NO_AVE:
                    pixelModeParams = new int[1];
                    break;
                case X_Y_EVERY_N:
                    pixelModeParams = new int[3];
                    break;
                case RAND:
                    pixelModeParams = new int[10];
            }
            for (int i=0; i<pixelModeParams.length; i++) {
                pixelModeParams[i] = decodeInt16(buffer, 15 + i*2);
            }
            int[] spectralData = new int[2048];
            int offset = 15 + pixelModeParams.length * 2;
            for (int i=0; i<spectralData.length; i++) {
                spectralData[i] = dataSize == DataSize.WORDs
                        ? decodeInt16(buffer, offset + i*2)
                        : decodeInt32(buffer, offset + i*4);
            }
            offset = offset + spectralData.length * 2;
            if ((buffer[offset] & 0xFF) != 0xFF || (buffer[offset+1] & 0xFF) != 0xFD) {
                throw new IllegalArgumentException("[GetSpectralAcquisition][BinaryCodec][decode] 频谱帧结束符错误");
            }
            return new Response(status, dataSize, scansAccumulated, integrationTimeMS, fpgaRegisterAddress, fpgaRegisterValue, pixelMode, pixelModeParams, spectralData);
        }
    }

}
