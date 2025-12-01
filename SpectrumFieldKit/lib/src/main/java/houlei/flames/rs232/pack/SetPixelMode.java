package houlei.flames.rs232.pack;

public interface SetPixelMode {

    enum PixelMode {
        ALL(0), N_NO_AVE(1), N_A(2), X_Y_EVERY_N(3), RAND(4);
        private final int value;

        PixelMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    class Request extends RequestPack {
        private final PixelMode pixelMode;
        private final int[] params;

        public Request(PixelMode mode, int ... params) {
            super('P');
            this.pixelMode = mode;
            this.params = params;
        }

        /**
         * All 2048 pixels
         */
        public Request() {
            this(PixelMode.ALL);
        }

        /**
         * Every n(th) pixel with no averaging
         */
        public Request(int n) {
            this(PixelMode.N_NO_AVE, n);
        }

        /**
         * pixel x through y every n pixels
         */
        public Request(int x, int y, int n){
            this(PixelMode.X_Y_EVERY_N, x,y,n);
        }

        /**
         * up to 10 randomly selected pixels
         */
        public Request(int[] points) {
            super('P');
            if (points == null || points.length == 0) {
                throw new IllegalArgumentException("Points must not be null or empty.");
            }
            pixelMode = PixelMode.RAND;
            params = new int[points.length+1];
            params[0] = points.length;
            System.arraycopy(points, 0, params, 1, points.length);
        }

        public PixelMode getPixelMode() {
            return pixelMode;
        }

        public int[] getParams() {
            return params;
        }

    }

    class Response extends ResponsePack {
        public Response(Status status) {
            super(status);
        }
    }

}
