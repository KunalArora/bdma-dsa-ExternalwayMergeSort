package ulb.dsa;

import ulb.dsa.io.InputStream;
import ulb.dsa.io.StreamResolver;

public class Printer {

    public static void main(String[] args) {
        StreamResolver resolver = new StreamResolver(2, 1000);
        InputStream in = resolver.newInputStream();
        in.open("RES.txt");
        int previous = Integer.MIN_VALUE;

        int c = 0;
        while (!in.endOfStream()){
            int cur = in.readNext();
            c++;
            if (previous > cur){
                System.out.println("not sorted");
                return;
            }
        }

        System.out.println(c);
        System.out.println("sorted");
    }
}
