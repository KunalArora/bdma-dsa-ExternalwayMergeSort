package ulb.dsa.io;

import java.io.*;

public class OutputStream2 implements OutputStream {

    private FileOutputStream fileOutputStream;
    private DataOutputStream dataOutputStream;
    private BufferedOutputStream bufferedOutputStream;

    @Override
    public void create(String fileName) {
        try {
            fileOutputStream = new FileOutputStream(new File(fileName));
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            dataOutputStream = new DataOutputStream(bufferedOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(int number) {
        try {
            dataOutputStream.writeInt(number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            dataOutputStream.close();
            bufferedOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
