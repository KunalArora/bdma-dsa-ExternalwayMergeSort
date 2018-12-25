package ulb.dsa;

import java.io.*;
import java.util.Random;

public class RandomGenerator {

    public static void main(String[] args) {
        Random random = new Random();
        long size = 10000000;


        try {
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("/Users/fernando/Documents/ulb/dsa/project/data/" + size + ".txt")));
            for (long i = 0; i < size; i++){
                out.writeInt(random.nextInt());
            }

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
