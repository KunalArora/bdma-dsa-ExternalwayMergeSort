package ulb.dsa.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class OutputStream4 implements OutputStream {

    private RandomAccessFile randomAccessFile;
    private MappedByteBuffer mappedByteBuffer;
    private FileChannel fileChannel;
    private int bSize;
    private int nextIndex;
    private long numWrites = 0;


    OutputStream4(int bufferSize) {
        this.bSize = (bufferSize / 4) * 4;
    }

    @Override
    public void create(String fileName) {
        try {
            File file = new File(fileName);
            randomAccessFile = new RandomAccessFile(file, "rw");
            fileChannel = randomAccessFile.getChannel();
            fileChannel.truncate(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(int number) {
        try {
            if(mappedByteBuffer == null || mappedByteBuffer.capacity() == mappedByteBuffer.position()) {
                mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, nextIndex*bSize, bSize);
                nextIndex+=1;
            }

            mappedByteBuffer.putInt(number);
            numWrites++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            mappedByteBuffer.clear();
            fileChannel.truncate(numWrites*Integer.BYTES);
            fileChannel.close();
            randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
