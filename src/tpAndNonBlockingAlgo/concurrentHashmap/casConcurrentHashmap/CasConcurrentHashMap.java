package tpAndNonBlockingAlgo.concurrentHashmap.casConcurrentHashmap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class CasConcurrentHashMap<K, V> {
    // Number of buckets in the hash table, should be a power of 2 for efficient bucket indexing using '&'
    private static final int BUCKET_COUNT = 16;

    // Array of AtomicReference for each bucket to allow CAS (Compare-And-Swap) operations
    private final AtomicReference<Node<K, V>>[] buckets;

    // Node class represents a single entry in the hash table
    private static class Node<K, V> {
        final K key; // Immutable key
        volatile V value; // Value can change, hence marked volatile for thread-safe visibility
        Node<K, V> next; // Pointer to the next node in the bucket's linked list

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    // Constructor initializes buckets with null references
    @SuppressWarnings("unchecked")
    public CasConcurrentHashMap() {
        // Using AtomicReference to store the head of the linked list for each bucket
        buckets = new AtomicReference[BUCKET_COUNT];
        for (int i = 0; i < BUCKET_COUNT; i++) {
            buckets[i] = new AtomicReference<>(null); // Initialize each bucket to an empty state
        }
    }

    // Calculate the bucket index for a given key using '&' for efficient modulo operation
    private int getBucketIndex(K key) {
        return key.hashCode() & (BUCKET_COUNT - 1);
    }

    // Inserts a key-value pair into the hash table
    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        AtomicReference<Node<K, V>> bucketHead = buckets[bucketIndex];

        while (true) {
            Node<K, V> head = bucketHead.get(); // Get the current head of the bucket
            Node<K, V> current = head;

            // Check if the key already exists in the bucket
            while (current != null) {
                if (current.key.equals(key)) {
                    // Key exists, update the value atomically
                    synchronized (current) { // Synchronize only on this specific node
                        current.value = value;
                    }
                    return; // Update is complete
                }
                current = current.next;
            }

            // Key does not exist, create a new node
            Node<K, V> newNode = new Node<>(key, value, head);

            // Use CAS to atomically update the bucket head
            if (bucketHead.compareAndSet(head, newNode)) {
                return; // Successfully inserted
            }

            // If CAS failed, another thread modified the bucket; retry
        }
    }

    // Retrieves the value associated with a key
    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        Node<K, V> current = buckets[bucketIndex].get(); // Get the current head of the bucket

        // Traverse the linked list to find the key
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value; // Key found, return its value
            }
            current = current.next;
        }

        return null; // Key not found
    }

    // Removes the key-value pair associated with a key
    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        AtomicReference<Node<K, V>> bucketHead = buckets[bucketIndex];

        while (true) {
            Node<K, V> head = bucketHead.get(); // Get the current head of the bucket
            Node<K, V> current = head;
            Node<K, V> prev = null;

            // Traverse the linked list to find the key
            while (current != null) {
                if (current.key.equals(key)) {
                    // Found the key, remove it
                    Node<K, V> newHead;

                    if (prev == null) {
                        // Removing the first node (head of the list)
                        newHead = current.next;
                    } else {
                        // Removing a node in the middle or end
                        newHead = head; // Head remains unchanged
                        prev.next = current.next; // Skip the current node
                    }

                    // Use CAS to update the bucket head
                    if (bucketHead.compareAndSet(head, newHead)) {
                        return; // Successfully removed
                    } else {
                        // CAS failed, retry
                        break;
                    }
                }

                prev = current;
                current = current.next;
            }

            // Key not found, exit
            return;
        }
    }

    // Prints the current state of the hash table (for debugging purposes)
    public void printTable() {
        for (int i = 0; i < BUCKET_COUNT; i++) {
            Node<K, V> current = buckets[i].get();
            System.out.print("Bucket " + i + ": ");
            while (current != null) {
                System.out.print("(" + current.key + ", " + current.value + ") -> ");
                current = current.next;
            }
            System.out.println("null");
        }
    }

    // Main method to demonstrate the tutorial
    public static void main(String[] args) throws InterruptedException {
        CasConcurrentHashMap<Integer, String> map = new CasConcurrentHashMap<>();

        // Number of threads and operations to simulate load
        int numThreads = 10;  // 10 threads
        int numOperationsPerThread = 1000;  // Each thread will perform 1000 operations

        // Executor to run concurrent threads
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Perform load test by having multiple threads running put, get, and remove operations
        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < numOperationsPerThread; j++) {
                    // Randomly decide whether to put, get, or remove
                    int key = (int) (Math.random() * 1000);  // Random key between 0 and 999
                    String value = "Value" + key;  // Corresponding value

                    // Simulate some operations
                    double operation = Math.random();
                    if (operation < 0.33) {
                        retryPut(map, key, value);  // Put operation
                    } else if (operation < 0.66) {
                        map.get(key);  // Get operation
                    } else {
                        retryRemove(map, key);  // Remove operation
                    }
                }
            });
        }

        // Wait for all threads to finish
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

//        // Optionally, print the final state of the map (for debugging purposes)
//        map.printTable();
//        // Adding key-value pairs with retries
//        retryPut(map, 1, "One");
//        retryPut(map, 2, "Two");
//        retryPut(map, 3, "Three");
//
//        // Retrieve values
//        System.out.println("Get 1: " + map.get(1)); // Output: One
//        System.out.println("Get 2: " + map.get(2)); // Output: Two
//
//        // Remove a key-value pair with retries
//        retryRemove(map, 1);

        System.out.println("Get 1 after removal: " + map.get(1)); // Output: null

        // Print the entire hash table
        map.printTable();
    }

    // Method to retry inserting key-value pairs
    public static <K, V> void retryPut(CasConcurrentHashMap<K, V> map, K key, V value) {
        int retries = 0;
        while (true) {
            try {
                map.put(key, value);
                System.out.println("Successfully inserted (" + key + ", " + value + ")");
                break; // Exit the loop if insertion succeeds
            } catch (Exception e) {
                retries++;
                System.out.println("CAS failed on put for (" + key + ", " + value + "), retrying... Retry count: " + retries);
                // Optionally, you can add a delay between retries, e.g. Thread.sleep(10);
            }
        }
    }

    // Method to retry removing key-value pairs
    public static <K, V> void retryRemove(CasConcurrentHashMap<K, V> map, K key) {
        int retries = 0;
        while (true) {
            try {
                map.remove(key);
                System.out.println("Successfully removed key " + key);
                break; // Exit the loop if removal succeeds
            } catch (Exception e) {
                retries++;
                System.out.println("CAS failed on remove for key " + key + ", retrying... Retry count: " + retries);
                // Optionally, you can add a delay between retries, e.g. Thread.sleep(10);
            }
        }
    }
}
