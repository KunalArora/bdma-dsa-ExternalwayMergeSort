package ulb.dsa.io;

import java.io.*;
import java.util.ArrayDeque;

public class InputStream3 implements InputStream {

    private FileInputStream fileInputStream;
    private DataInputStream dataInputStream;
    private int bSize;
    private ArrayDeque<Integer> buffer;

    InputStream3(int bufferSize) {
        this.bSize = bufferSize / 4;
    }

    @Override
    public void open(String filePath) {
        try {
            fileInputStream = new FileInputStream(new File(filePath));
            dataInputStream = new DataInputStream(fileInputStream);
            buffer = new ArrayDeque<>(bSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int readNext() {
        try {
            if(buffer.isEmpty()) {
                fillBuffer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.poll();
    }

    @Override
    public boolean endOfStream() {
        try {
            return buffer.isEmpty() && dataInputStream.available() <= 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void fillBuffer() throws IOException {
        while(buffer.size() < bSize && dataInputStream.available() > 0) {
            buffer.offer(dataInputStream.readInt());
        }
    }
}
