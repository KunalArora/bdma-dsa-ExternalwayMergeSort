package ulb.dsa.io;

import java.io.*;

public class InputStream1 implements InputStream {

    private FileInputStream fileInputStream;
    private DataInputStream dataInputStream;

    @Override
    public void open(String filePath) {
        try {
            fileInputStream = new FileInputStream(new File(filePath));
            dataInputStream = new DataInputStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int readNext() {
        try {
            return dataInputStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean endOfStream() {

        try {
            return dataInputStream.available() <= 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
