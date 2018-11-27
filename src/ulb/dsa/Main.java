package ulb.dsa;

import ulb.dsa.io.StreamResolver;

public class Main {

    /**
     * @param args
     * path_to_data_file
     * stream_type -> Integer ∈ {1,2,3,4})
     * [k]number_of_IO_streams -> Integer
     * [B]buffer_size -> Integer (kbytes)
     * [M]initial_memory_available -> Integer (kbytes)
     * [d]number_of_sorting_streams -> Integer
     */
    public static void main(String[] args) {
        String pathToDataFile = args[0];

        int streamType = Integer.parseInt(args[1]);
        int numberOfIOStreams = Integer.parseInt(args[2]);
        int bufferSize = Integer.parseInt(args[3]);
        StreamResolver streamResolver = new StreamResolver(streamType, numberOfIOStreams, bufferSize);

        int initialMemAvailable = Integer.parseInt(args[4]);
        int numberOfSortingStreams = Integer.parseInt(args[5]);

        MultiwayMerge multiwayMerge = new MultiwayMerge(streamResolver, initialMemAvailable, numberOfSortingStreams);
        multiwayMerge.sort(pathToDataFile);

    }
}
