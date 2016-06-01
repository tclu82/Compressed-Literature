import java.util.ArrayList;
import java.util.List;

/**
 * Extra credits
 * MyPriorityQueue class
 *
 * @author Tzu-Chien Lu tclu82@uw.edu
 * @version Winter 2016
 */
public class MyPriorityQueue <T extends Comparable<T>> {
    
    /** A list to contain elements. */
    private List<T> myList;
    
    /** A size represents elements quantity */
    private int mySize;
    
    /**
     * Constructor
     */
    public MyPriorityQueue() {
        mySize = 0;
        myList = new ArrayList<T>();
        
        /** MyPQ starts at 1 */
        myList.add(null);
    }
    
    /**
     * Offer new item to MyPQ
     * 
     * @param item 
     */
    public void offer(T item) {
        myList.add(item);
        mySize += 1; 
        
        for (int k = mySize; k > 1; k /= 2) {
        	
        	/** Swap, make PriorityQueue happen */
            if (item.compareTo(myList.get(k / 2)) < 0) {
                final T temp = myList.get(k);
                myList.set(k, myList.get(k / 2));
                myList.set(k / 2, temp);
            }
        }
    }
    
    /**
     * Poll up T
     * 
     * @return T
     */
    public T poll() {
    	
    	/** Underflow*/
        if (myList.isEmpty()) return null;
        
        else {
            final T item = myList.get(1);
            myList.set(1, myList.get(mySize)); 
            myList.remove(mySize);             
            mySize--;
            
            if (mySize > 1) heapify();
            
            return item;
        }
    }
    
    /**
     * Implement MyPQ with Heap structure.
     */
    private void heapify() {
        final T last = myList.get(1);
        int child = 1;
        int index = 1;
        
        while (2 * index <= mySize) {
            child = 2 * index;
            
            if (child < mySize && (myList.get(child).compareTo(myList.get(child + 1))) > 0) 
            	child++;
            
            if (last.compareTo(myList.get(child)) > 0) {
                final T temp = myList.get(index);
                myList.set(index, myList.get(child));
                myList.set(child, temp);
                index = child;
                
            } else break;
        }
    }

    /**
     * Check if MyPQ is empty.
     * 
     * @return boolean
     */
    public boolean isEmpty() {
        return mySize == 0;
    }
   
    /**
     * Getter method
     * 
     * @return integer of mySize
     */
    public int size() {
        return mySize;
    }
    
    /**
     * toString to print MyPQ
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder("[");
        sb.append(myList.get(0));
        
        for (int i = 1; i < myList.size(); i++) {
            sb.append(", ");
            sb.append(myList.get(i));
        }
        sb.append("]");
        return sb.toString();
    }
}
