import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rQueue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i += 1) {
            System.out.println(rQueue.dequeue());
        }
    }
}
