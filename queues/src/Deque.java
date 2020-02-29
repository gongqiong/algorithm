import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Requirements: constant worst-case time;
 * at most 48n + 192 bytes of memory
 */
public class Deque<Item> implements Iterable<Item> {
    private final Node head;
    private final Node tail;
    private int size;
    
    private class Node {
        Item item;
        Node prev;
        Node next;
    }
    
    // construct an empty deque
    public Deque() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
        size = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // return the number of items on the deque
    public int size() {
        return size;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("can not add null item");
        }
        Node node = new Node();
        node.item = item;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        node.prev = head;
        size += 1;
    }
    
    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("can not add null item");
        }
        Node node = new Node();
        node.item = item;
        node.prev = tail.prev;
        tail.prev.next = node;
        node.next = tail;
        tail.prev = node;
        size += 1;
    }
    
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot remove item from an empty deque");
        }
        Node node = head.next;
        head.next = node.next;
        head.next.prev = head;
        size -= 1;
        return node.item;
    }
    
    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot remove item from an empty deque");
        }
        Node node = tail.prev;
        tail.prev = node.prev;
        tail.prev.next = tail;
        size -= 1;
        return node.item;
    }
    
    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node curr = head;
        
        @Override
        public boolean hasNext() {
            return curr.next != tail;
        }
        
        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no more items to return");
            }
            curr = curr.next;
            return curr.item;
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
        Deque<Integer> d = new Deque<>();
        d.addFirst(1);
        d.print();
        d.addLast(2);
        d.print();
        d.removeFirst();
        d.print();
        d.removeLast();
        d.print();
        d.addLast(3);
        d.print();
    }
}
