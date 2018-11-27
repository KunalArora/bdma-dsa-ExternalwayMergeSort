package ulb.dsa;

import ulb.dsa.io.InputStream;
import ulb.dsa.io.OutputStream;
import ulb.dsa.io.StreamResolver;

public class MultiwayMerge {
    private StreamResolver streamResolver;
    private int initialMemAvailable;
    private int numberOfSortingStreams;

    public MultiwayMerge(StreamResolver streamResolver, int initialMemAvailable, int numberOfSortingStreams) {
        this.streamResolver = streamResolver;
        this.initialMemAvailable = initialMemAvailable;
        this.numberOfSortingStreams = numberOfSortingStreams;
    }

    public void sort(String pathToDataFile) {
        InputStream inputStream = streamResolver.newInputStream();
        OutputStream outputStream = streamResolver.newOutputStream();
    }
}
