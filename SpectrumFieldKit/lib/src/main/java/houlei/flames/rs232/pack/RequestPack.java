package houlei.flames.rs232.pack;

public class RequestPack {
    protected final char key;

    public RequestPack(char key) {
        this.key = key;
    }

    public char getKey() {
        return key;
    }

    @Override
    public String toString() {
        int index = getClass().getName().lastIndexOf('.');
        String className = getClass().getCanonicalName().substring(index+1);
        return className +
                "{ command: " + key + " }";
    }
}
