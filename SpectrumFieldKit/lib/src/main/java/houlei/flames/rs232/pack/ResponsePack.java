package houlei.flames.rs232.pack;

public class ResponsePack {

    public enum Status {
        ACK((char)6), NAK((char)21),STX((char)2),ETX((char)3);
        private final char value;

        Status(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }

        @Override
        public String toString() {
            return name() + "(" + (int)value + ")";
        }
    }

    protected final Status status;

    public ResponsePack(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
    @Override
    public String toString() {
        int index = getClass().getName().lastIndexOf('.');
        String className = getClass().getCanonicalName().substring(index+1);
        return className +
                "{ status: " + status.name() + "(" + (int) status.getValue() + ") }";
    }
}
