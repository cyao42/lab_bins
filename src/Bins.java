import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Runs a number of algorithms that try to fit files onto disks.
 */
public class Bins {
    public static final String DATA_FILE = "example.txt";

    /**
     * Reads list of integer data from the given input.
     *
     * @param input tied to an input source that contains space separated numbers
     * @return list of the numbers in the order they were read
     */
    public List<Integer> readData (Scanner input) {
        List<Integer> results = new ArrayList<Integer>();
        int total = 0;
        while (input.hasNext()) {
        	int fileSize = input.nextInt();
        	total += fileSize;
            results.add(fileSize);
        }
        System.out.println("total size = " + total / 1000000.0 + "GB");
        return results;
    }
    
    public static PriorityQueue<Disk> loop(List<Integer> data) { 
        PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
        if (data.size() == 0) return pq;
        pq.add(new Disk(0));
        int diskId = 1;
        for (Integer size : data) {
            Disk d = pq.peek();
            if (d.freeSpace() >= size) {
                pq.poll();
                d.add(size);
                pq.add(d);
            } else {
                Disk d2 = new Disk(diskId);
                diskId++;
                d2.add(size);
                pq.add(d2);
            }
        }
    	return pq;
    }
    
    public static void printResults(PriorityQueue<Disk> pq, String type) { 
        System.out.println();
        System.out.println(type + " method");
        System.out.println("number of pq used: " + pq.size());
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
        System.out.println();
    }
    
    public static void inOrder(List<Integer> data) { 
    	PriorityQueue<Disk> qu = loop(data); 
    	printResults(qu, "worst-fit");
    }
    
    public static void decreasingOrder(List<Integer> data) { 
    	PriorityQueue<Disk> qu = loop(data); 
    	printResults(qu, "worst-fit decreasing order");
    }

    /**
     * The main program.
     */
    public static void main (String args[]) {
        Bins b = new Bins();
        Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
        List<Integer> data = b.readData(input);
        inOrder(data);
        decreasingOrder(data);
    }
}
