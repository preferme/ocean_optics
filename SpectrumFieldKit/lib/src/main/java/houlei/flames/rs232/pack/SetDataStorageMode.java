package houlei.flames.rs232.pack;

public interface SetDataStorageMode {

    enum StorageMode {
        Transmitted(0), Stored(1);
        private final int value;

        StorageMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    class Request extends RequestPack {
        private final StorageMode value;

        public Request(StorageMode value) {
            super('M');
            this.value = value == null ? StorageMode.Transmitted : value;
        }

        public StorageMode getValue() {
            return value;
        }
    }

    class Response extends ResponsePack {
        public Response(Status status) {
            super(status);
        }
    }

}
