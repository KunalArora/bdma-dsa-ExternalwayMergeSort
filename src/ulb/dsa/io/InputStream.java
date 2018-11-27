package ulb.dsa.io;

public interface InputStream {

    void open(String filePath);
    int readNext();
    boolean endOfStream();
}
