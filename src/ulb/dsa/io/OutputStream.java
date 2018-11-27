package ulb.dsa.io;

public interface OutputStream {
    public static int numberOfIOStreams = 0;
    public static int bufferSize = 0;
    void create(String fileName);
    void write(int number);
    void close();
}
