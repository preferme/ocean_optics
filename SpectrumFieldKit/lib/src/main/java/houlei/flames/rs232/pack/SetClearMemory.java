package houlei.flames.rs232.pack;

@Deprecated
public interface SetClearMemory {
    // 3.8 Clear Memory
    enum ClearMemory {
        CLEAR_MEMORY_0, CLEAR_MEMORY_1
    }

    class Request extends RequestPack {
        private final ClearMemory value;

        public Request(ClearMemory value) {
            super('L');
            this.value = value;
            if (value == null) {
                throw new IllegalArgumentException("Value must be not null.");
            }
        }

        public ClearMemory getValue() {
            return value;
        }
    }

    class Response extends ResponsePack {
        public Response(Status status) {
            super(status);
        }
    }

}
