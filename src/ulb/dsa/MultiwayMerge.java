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

    public MultiwayMerge(StreamResolver streamResolver, int initialMemAvailable, int numberOfSortingStreams) {

        if (numberOfSortingStreams < 2){
            throw new IllegalArgumentException("Well and how would you sort array in memory with a single sorting stream?");
        }  

        if (initialMemAvailable < 2){
            throw new IllegalArgumentException("Well and how would you sort array in memory with this much memory?");
        }

        this.streamResolver = streamResolver;
        this.initialMemAvailable = initialMemAvailable;
        this.numberOfSortingStreams = numberOfSortingStreams;
    }

    public void sort(String pathToDataFile) {

        InputStream inputStream = streamResolver.newInputStream();
        InputStream tmpIn = streamResolver.newInputStream();
        OutputStream tmpOut = streamResolver.newOutputStream();

        int cntr_file = 0;

        inputStream.open(pathToDataFile);
        tmpOut.create("tmp/tmp" + cntr_file + ".txt");
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        LinkedList<InputStream> stream_hld = new LinkedList<InputStream>();

        int cnt_inp = 0;

        while (!inputStream.endOfStream()) {

            int tmp = inputStream.readNext();
            pq.add(tmp);

            if (pq.size() == this.initialMemAvailable){
                while (!pq.isEmpty()) {
                    tmpOut.write(pq.remove());
                    ++cnt_inp;
                }
                tmpOut.close();
                tmpIn = streamResolver.newInputStream();
                tmpIn.open("tmp/tmp" + cntr_file + ".txt");
                stream_hld.addLast(tmpIn);
                ++cntr_file;
                tmpOut.create("tmp/tmp" + cntr_file + ".txt");
            }
        }

        if (pq.size() != 0){
            while (!pq.isEmpty()) {
                tmpOut.write(pq.remove());
                ++cnt_inp;
            }
            tmpOut.close();
            tmpIn = streamResolver.newInputStream();
            tmpIn.open("tmp/tmp" + cntr_file + ".txt");
            stream_hld.addLast(tmpIn);
        }

        while(true){
            List<InputStream> inps = new ArrayList(this.numberOfSortingStreams);
            
            int lcl_cnt = 0;

            for (int j = 0; j < this.numberOfSortingStreams; ++j){
                if (stream_hld.size() > 0){
                    inps.add(stream_hld.removeFirst());
                    ++lcl_cnt;
                }
            }

            ++cntr_file;

            if (stream_hld.size() == 0){
                merge(inps, "./RES.txt");
            }
            else{
                merge(inps, "tmp/tmp" + cntr_file + ".txt");
            }

            if (stream_hld.size() > 0){

                tmpIn = streamResolver.newInputStream();
                tmpIn.open("tmp/tmp" + cntr_file + ".txt");
                stream_hld.addLast(tmpIn);

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

        int top_elem, top_ind;

        for (int i = 0; i < inps.size(); ++i){
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



