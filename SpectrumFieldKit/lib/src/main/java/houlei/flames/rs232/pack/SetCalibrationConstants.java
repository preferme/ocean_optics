package houlei.flames.rs232.pack;

public interface SetCalibrationConstants {

    enum CalibrationConstants {
        SerialNumber(0),
        Wavelength0(1), Wavelength1(2), Wavelength2(3), Wavelength3(4),
        StrayLight(5),
        NonLinearity0(6), NonLinearity1(7), NonLinearity2(8), NonLinearity3(9),
        NonLinearity4(10), NonLinearity5(11), NonLinearity6(12), NonLinearity7(13),
        Polynomial(14), OpticalBenchConf(15), FlameConf(16),
        Reserved17(17), Reserved18(18), Reserved19(19);

        private final int value;

        CalibrationConstants(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    class Request extends RequestPack {
        private final CalibrationConstants constants;
        private final String value;

        public Request(CalibrationConstants constants, String value) {
            super('x');
            this.constants = constants;
            this.value = value;
        }

        public CalibrationConstants getConstants() {
            return constants;
        }

        public String getValue() {
            return value;
        }
    }

    class Response extends ResponsePack {
        public Response(Status status) {
            super(status);
        }
    }

}
