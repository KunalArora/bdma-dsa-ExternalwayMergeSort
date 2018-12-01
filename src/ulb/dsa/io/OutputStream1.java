package ulb.dsa.io;

import java.io.*;

public class OutputStream1 implements OutputStream {

    private FileOutputStream fileOutputStream;
    private DataOutputStream dataOutputStream;

    @Override
    public void create(String fileName) {
        try {
            File file = new File(fileName);
            file.delete();
            fileOutputStream = new FileOutputStream(file);
            dataOutputStream = new DataOutputStream(fileOutputStream);
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
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
