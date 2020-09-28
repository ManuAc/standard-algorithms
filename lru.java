
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class LRUCache {
    
    private final int size = 3;
    private final Map<Integer, Integer> cache = new HashMap<>();
    
    private final Deque<Integer> keys = new LinkedList<>();
    private final Set<Integer> keyReference = new HashSet<>();
    
    public Integer get(Integer key){
        
        // No need to be thread safe for read operation
        Integer value = cache.get(key);
        
        if(value != null) {
            
            synchronized(this) {
                access(key);
            }

            return value;
        }
        
        // TODO: Accessing the next level cache
        return value;
    }
    
    public void set(Integer key,Integer value){
        
        // Allow one thread to access at once
        synchronized(this) {
            
            int currentSize = cache.size();
            
            if(currentSize >= size) {
                // Insert into next level
                
            }
            
            /*
             If the level is full, then evict an element from that level and also update
             the order in keys queue
             */
            access(key);
            
            // Insert the new element
            cache.put(key, value);
        }
    }
    
    private void access(Integer key) {
        
        /*
         If cache miss then remove the last element and
         insert the new element at the front of the queue
        */
        if(!keyReference.contains(key) && keys.size() == size) {

            int lastElement = keys.removeLast();
            keyReference.remove(lastElement);  
            
            // Remove the key-value pair from actual store as well
            cache.remove(lastElement);
        } else {
            
            if(!keys.isEmpty()) {
                // If cache hit then remove the element and push to the front
                keys.remove(key);
            }
        }
        
        keys.push(key);
        keyReference.add(key);
    }
    
    public void display() {
        
        for(Map.Entry<Integer, Integer> entry : cache.entrySet()) {
            System.out.println(String.format("Key: %d, Value: %d", entry.getKey(), entry.getValue()));
        }
    }
    
    public static void main(String args[] ) throws Exception {
            
        LRUCache cache = new LRUCache();
        cache.set(1, 10);
        cache.set(2, 20);
        cache.set(3, 30);
        cache.get(1);
        cache.set(4, 40);
        cache.get(3);
        
        cache.set(5, 50);
        // The elements in the queue should be 1, 3, and 4
        cache.display();
    }
}
