package ulb.dsa.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.io.File;

public class InputStream4 implements InputStream {

    private RandomAccessFile randomAccessFile;
    private MappedByteBuffer mappedByteBuffer;
    private FileChannel fileChannel;
    private long upperBound = 0;
    private long nextPosition = 0;
    int bSize;

    InputStream4(int bufferSize) {
        this.bSize = (bufferSize / 4) * 4;
    }

    @Override
    public void open(String filePath) {
        try {
            File file = new File(filePath);
            randomAccessFile = new RandomAccessFile(file, "rw");
            fileChannel = randomAccessFile.getChannel();
            upperBound = Math.min(fileChannel.size(), bSize);
            mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, upperBound);
            nextPosition = upperBound;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int readNext() {
        if (!mappedByteBuffer.hasRemaining()) {
            if (allocate()) {
                throw new RuntimeException("Reading from a empty stream");
            }
        }
        return mappedByteBuffer.getInt();
    }

    @Override
    public boolean endOfStream() {
        if(mappedByteBuffer == null) {
            return true;
        } else if(mappedByteBuffer.hasRemaining()) {
            return false;
        } else {
            return allocate();
        }
    }

    private boolean allocate() {
        try {
            if(nextPosition >= fileChannel.size()) {
                return true;
            } else {
                long remSize = Math.min(bSize, fileChannel.size() - nextPosition);
                mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, nextPosition, remSize);
                nextPosition += remSize;
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }
}
