package ulb.dsa.io;

import java.io.*;
import java.util.ArrayDeque;

public class OutputStream3 implements OutputStream {

    private FileOutputStream fileOutputStream;
    private DataOutputStream dataOutputStream;
    int bSize;
    private ArrayDeque<Integer> buffer;

    OutputStream3(int bufferSize) {
        this.bSize = bufferSize;
        buffer = new ArrayDeque<>(bSize);
    }

    @Override
    public void create(String fileName) {
        try {
            fileOutputStream = new FileOutputStream(new File(fileName));
            dataOutputStream = new DataOutputStream(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(int number) {
        if(buffer.size() == bSize) {
            flushBuffer();
            buffer.clear();
        } else {
            buffer.offer(number);
        }
    }

    @Override
    public void close() {
        try {
            flushBuffer();
            dataOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void flushBuffer() {
        try {
            while(buffer.size() != 0) {
                dataOutputStream.writeInt(buffer.poll());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
