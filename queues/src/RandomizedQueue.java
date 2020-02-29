import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * randomized queue operation: constant amortized time
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rqArr;
    private int size;
    private int capacity;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        capacity = 8;
        rqArr = (Item[]) new Object[capacity];
        size = 0;
    }
    
    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // return the number of items on the randomized queue
    public int size() {
        return size;
    }
    
    // resize the array
    private void resize() {
        Item[] arr;
        if (capacity == size) {
            capacity *= 2;
            arr = (Item[]) new Object[capacity];
            System.arraycopy(rqArr, 0, arr, 0, size);
            rqArr = arr;
        }
        if (capacity > 8 && capacity > 4 * size) {
            capacity = 2 * size;
            arr = (Item[]) new Object[capacity];
            System.arraycopy(rqArr, 0, arr, 0, size);
            rqArr = arr;
        }
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("can not add null item");
        }
        resize();
        rqArr[size] = item;
        size += 1;
    }
    
    //  remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("can not remove from an empty randomized deque");
        }
        resize();
        int index = StdRandom.uniform(size);
        Item item = rqArr[index];
        rqArr[index] = rqArr[size - 1];
        rqArr[size - 1] = null;
        size -= 1;
        return item;
    }
    
    // return a random item (but not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("can not remove from an empty randomized deque");
        }
        return rqArr[StdRandom.uniform(size)];
    }
    
    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RQIterator();
    }
    
    private class RQIterator implements Iterator<Item> {
        Item[] copiedArr;
        int restSize = size;
        
        RQIterator() {
            copiedArr = (Item[]) new Object[size];
            System.arraycopy(rqArr, 0, copiedArr, 0, size);
        }
        
        @Override
        public boolean hasNext() {
            return restSize != 0;
        }
        
        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no more items to return");
            }
            int index = StdRandom.uniform(restSize);
            Item item = copiedArr[index];
            copiedArr[index] = copiedArr[restSize - 1];
            copiedArr[restSize - 1] = null;
            restSize -= 1;
            return item;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove unsupported");
        }
    }
    
    private void print() {
        for (Item item : this) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
    
    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> pQueue = new RandomizedQueue<>();
        for (int i = 0; i < 10; i += 1) {
            pQueue.enqueue(i);
        }
    }
}
