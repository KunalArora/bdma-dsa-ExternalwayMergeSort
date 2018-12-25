package ulb.dsa.benchmark;

import ulb.dsa.io.*;

public class IOBenchmark {

    public static void main(String[] args) {

        int numStreams = Integer.parseInt(args[0]);
        int numRepeats = Integer.parseInt(args[1]);
        int streamType = Integer.parseInt(args[2]);
        int bufferSize = Integer.parseInt(args[3]);
        int fileSize = Integer.parseInt(args[4]);

        StreamResolver streamResolver = new StreamResolver(streamType, bufferSize);

        InputStream ins[] = new InputStream[numStreams];
        OutputStream outs[] = new OutputStream[numStreams];
        for (int i = 0; i < numStreams; i++) {
            InputStream inputStream = streamResolver.newInputStream();
            inputStream.open("data/" + fileSize + "_" + i +".txt");
            ins[i] = inputStream;

            OutputStream outputStream = streamResolver.newOutputStream();
            outputStream.create("tmp/" + i + ".txt");
            outs[i] = outputStream;
        }

        for (int l = 0; l < numRepeats; l++) {
            for (int i = 0; i < numStreams; i++) {
                int next = ins[i].readNext();
                outs[i].write(next);
            }
        }

        for (int i = 0; i < numStreams; i++) {
            outs[i].close();
        }
    }
}
