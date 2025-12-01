package houlei.flames.rs232.pack;

public interface SetLampEnable {

    class Request extends RequestPack {

        public enum Light {
            OFF, ON
        }

        private final Light light;
        public Request(Light light) {
            super('J');
            this.light = light == null ? Light.OFF : light;
        }

        public Light getLight() {
            return light;
        }
    }

    class Response extends ResponsePack {
        public Response(Status status) {
            super(status);
        }
    }
}
