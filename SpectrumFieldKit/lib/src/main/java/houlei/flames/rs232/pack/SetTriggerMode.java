package houlei.flames.rs232.pack;

public interface SetTriggerMode {

    enum TriggerMode {
        Normal(0), Software(1),
        ExternalHardwareLevel(2),
        ExternalSynchronization(3),
        ExternalHardwareEdge(4);

        private final int value;

        TriggerMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    class Request extends RequestPack {
        private final TriggerMode triggerMode;

        public Request(TriggerMode triggerMode) {
            super('T');
            this.triggerMode = triggerMode == null ? TriggerMode.Normal : triggerMode;
        }

        public TriggerMode getTriggerMode() {
            return triggerMode;
        }

    }

    class Response extends ResponsePack {
        public Response(Status status) {
            super(status);
        }
    }

}
