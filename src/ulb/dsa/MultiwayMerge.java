package ulb.dsa;

import ulb.dsa.io.InputStream;
import ulb.dsa.io.OutputStream;
import ulb.dsa.io.StreamResolver;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class MultiwayMerge {
    private StreamResolver streamResolver;
    private int initialMemAvailable;
    private int numberOfSortingStreams;
    private int numberOfIOStreams;

    public MultiwayMerge(StreamResolver streamResolver, int initialMemAvailable, int numberOfSortingStreams, int numberOfIOStreams) {

        if (numberOfSortingStreams < 2){
            throw new IllegalArgumentException("Well and how would you sort array in memory with a single sorting stream?");
        }  

        if (initialMemAvailable < 2){
            throw new IllegalArgumentException("Well and how would you sort array in memory with this much memory?");
        }

        this.streamResolver = streamResolver;
        this.initialMemAvailable = initialMemAvailable;
        this.numberOfSortingStreams = numberOfSortingStreams;
        this.numberOfIOStreams = numberOfIOStreams;
    }

    public void sort(String pathToDataFile) {

        InputStream inputStream = streamResolver.newInputStream();
        InputStream tmpIn;
        OutputStream tmpOut = streamResolver.newOutputStream();

        int cntr_file = 0;

        inputStream.open(pathToDataFile);
        tmpOut.create("../tmp/tmp" + cntr_file + ".txt");
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        LinkedList<String> stream_hld = new LinkedList<>();

        while (!inputStream.endOfStream()) {

            int tmp = inputStream.readNext();
            pq.add(tmp);

            if (pq.size() == this.initialMemAvailable){
                while (!pq.isEmpty()) {
                    tmpOut.write(pq.remove());
                }
                tmpOut.close();
                stream_hld.addLast("../tmp/tmp" + cntr_file + ".txt");
                ++cntr_file;
                tmpOut.create("../tmp/tmp" + cntr_file + ".txt");
            }
        }

        if (pq.size() != 0){
            while (!pq.isEmpty()) {
                tmpOut.write(pq.remove());
            }
            tmpOut.close();
            stream_hld.addLast("../tmp/tmp" + cntr_file + ".txt");
        }

        int stream_bound;
        if (this.numberOfIOStreams > this.numberOfSortingStreams){
            stream_bound = this.numberOfSortingStreams;
        }
        else{
            stream_bound = this.numberOfIOStreams;
        }

        List<InputStream> inps = new ArrayList(stream_bound);
        for (int j = 0; j < stream_bound; ++j) {
            tmpIn = streamResolver.newInputStream();
            inps.add(tmpIn);
        }

        while(true){


            for (int j = 0; j < stream_bound; ++j){
                if (stream_hld.size() > 0){
                    inps.get(j).open(stream_hld.removeFirst());
                }
            }

            ++cntr_file;

            if (stream_hld.size() == 0){
                merge(inps, "./RES.txt");
            }
            else{
                merge(inps, "../tmp/tmp" + cntr_file + ".txt");
            }

            if (stream_hld.size() > 0){

                stream_hld.addLast("../tmp/tmp" + cntr_file + ".txt");

            }
            else{
                System.out.println(stream_hld.size());
                break;
            }
        }
    }

    private void merge(List<InputStream> inps, String filePath){

        OutputStream dataOut = this.streamResolver.newOutputStream();
        dataOut.create(filePath);

        PriorityQueue<Integer> pQueue = new PriorityQueue<>(); 
        List<Integer> check_elems = new ArrayList(inps.size());

        int top_elem;

        for (int i = 0; i < inps.size(); ++i){
            if (inps.get(i).endOfStream()){
                break;
            }
            check_elems.add(inps.get(i).readNext());
            pQueue.add(check_elems.get(i));
        }

        while (!pQueue.isEmpty()) {

            top_elem = pQueue.remove();
            dataOut.write(top_elem);

            for (int iter=0; iter < check_elems.size(); ++iter){
                if ((check_elems.get(iter) == top_elem) && (!inps.get(iter).endOfStream())){
                    check_elems.set(iter, inps.get(iter).readNext());
                    pQueue.add(check_elems.get(iter));
                    break;
                }
            }
        }
        dataOut.close();
    }
}



