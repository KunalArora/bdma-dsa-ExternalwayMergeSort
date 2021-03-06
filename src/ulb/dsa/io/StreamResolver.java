package ulb.dsa.io;

public class StreamResolver {
    private int streamType;
    private int bufferSize;

    public StreamResolver(int streamType, int bufferSize) {
        this.streamType = streamType;
        this.bufferSize = bufferSize;
    }

    public InputStream newInputStream(){
        switch (streamType) {
            case 1:
                return new InputStream1();
            case 2:
                return new InputStream2();
            case 3:
                return new InputStream3(bufferSize);
            case 4:
                return new InputStream4(bufferSize);
        }
        return null;
    }

    public OutputStream newOutputStream(){
        switch (streamType) {
            case 1:
                return new OutputStream1();
            case 2:
                return new OutputStream2();
            case 3:
                return new OutputStream3(bufferSize);
            case 4:
                return new OutputStream4(bufferSize);
        }
        return null;
    }
}
