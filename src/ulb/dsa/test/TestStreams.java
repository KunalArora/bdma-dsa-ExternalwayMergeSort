package ulb.dsa.test;

import ulb.dsa.io.InputStream;
import ulb.dsa.io.OutputStream;
import ulb.dsa.io.StreamResolver;
import java.io.File;

public class TestStreams {

    public static void testStreams(String pathToInputFile, String pathToOutputFile, StreamResolver streamResolver) {
        File dir = new File("output");
        if(!dir.exists()) dir.mkdir();
        InputStream inputS = streamResolver.newInputStream();
        OutputStream outS = streamResolver.newOutputStream();
        inputS.open(pathToInputFile);
        outS.create(dir + "/" + pathToOutputFile);
        while(!inputS.endOfStream()){
            int v = inputS.readNext();
            System.out.println(v);
            outS.write(v);
        }
        outS.close();
    }
}
