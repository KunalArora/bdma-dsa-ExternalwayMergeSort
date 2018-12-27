package ulb.dsa;

import ulb.dsa.io.StreamResolver;
import ulb.dsa.io.InputStream;
import ulb.dsa.io.OutputStream;
import ulb.dsa.io.InputStream;
import ulb.dsa.test.TestStreams;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Main {

    /**
     * @param args
     * path_to_data_file
     * stream_type -> Integer ∈ {1,2,3,4})
     * [k]number_of_IO_streams -> Integer
     * [B]buffer_size -> Integer (bytes)
     * [M]initial_memory_available -> Integer (bytes)
     * [d]number_of_sorting_streams -> Integer
     */
    public static void main(String[] args) {
        String pathToDataFile = args[0];

        int streamType = Integer.parseInt(args[1]);
        int numberOfIOStreams = Integer.parseInt(args[2]);
        int bufferSize = Integer.parseInt(args[3]);
        StreamResolver streamResolver = new StreamResolver(streamType, bufferSize);
        

        int initialMemAvailable = Integer.parseInt(args[4]);
        int numberOfSortingStreams = Integer.parseInt(args[5]);

        MultiwayMerge multiwayMerge = new MultiwayMerge(streamResolver, initialMemAvailable, numberOfSortingStreams, numberOfIOStreams);
        multiwayMerge.sort(pathToDataFile);

        InputStream inputStream = streamResolver.newInputStream();
        inputStream.open("./RES.txt");

        int cnt = 0;
        ArrayList<Integer> check_res = new ArrayList<>();
        while (!inputStream.endOfStream()) {
            int tmp = inputStream.readNext();
            check_res.add(tmp);
            ++cnt;
        }

        System.out.println("Total len of res file:" + cnt);
        System.out.println("Result is sorted:" + isCollectionSorted(check_res));
    }

    static private boolean isCollectionSorted(List list) {
        List copy = new ArrayList(list);
        Collections.sort(copy);
        return copy.equals(list);
    }
}
