import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;

public class ConcurrentHashTable<K, V> {
    private static final int BUCKET_COUNT = 16; // Number of buckets
    private final Node<K, V>[] buckets; // Array of buckets
    private final ReentrantLock[] locks; // Array of locks for each bucket

    // Node class for linked list in each bucket
    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // Constructor initializes buckets with dummy head nodes and locks
    @SuppressWarnings("unchecked")
    public ConcurrentHashTable() {
        buckets = new Node[BUCKET_COUNT];
        locks = new ReentrantLock[BUCKET_COUNT];
        for (int i = 0; i < BUCKET_COUNT; i++) {
            buckets[i] = new Node<>(null, null); // Dummy head node for each bucket
            locks[i] = new ReentrantLock(); // Initialize a lock for each bucket
        }
    }

    // Calculates the bucket index based on the key's hash code
    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % BUCKET_COUNT);
    }

//    private int getBucketIndex(K key) {
//        return key.hashCode() & (BUCKET_COUNT - 1);
//    }


    // Puts a key-value pair into the hash table
    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        ReentrantLock lock = locks[bucketIndex];
        lock.lock(); // Acquire the lock for the specific bucket
        try {
            Node<K, V> head = buckets[bucketIndex];
            Node<K, V> current = head.next;

            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = value; // Update the value if the key exists
                    return;
                }
                current = current.next;
            }

            // Insert new node at the head (after the dummy node)
            Node<K, V> newNode = new Node<>(key, value);
            newNode.next = head.next;
            head.next = newNode;
        } finally {
            lock.unlock(); // Release the lock
        }
    }

    // Retrieves the value associated with a key
    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        ReentrantLock lock = locks[bucketIndex];
        lock.lock(); // Acquire the lock for the specific bucket
        try {
            Node<K, V> current = buckets[bucketIndex].next; // Skip the dummy node
            while (current != null) {
                if (current.key.equals(key)) {
                    return current.value; // Return the value if found
                }
                current = current.next;
            }
            return null; // Key not found
        } finally {
            lock.unlock(); // Release the lock
        }
    }

    // Removes the key-value pair associated with a key
    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        ReentrantLock lock = locks[bucketIndex];
        lock.lock(); // Acquire the lock for the specific bucket
        try {
            Node<K, V> head = buckets[bucketIndex];
            Node<K, V> current = head.next;
            Node<K, V> prev = head;

            while (current != null) {
                if (current.key.equals(key)) {
                    prev.next = current.next; // Remove the current node
                    return;
                }
                prev = current;
                current = current.next;
            }
        } finally {
            lock.unlock(); // Release the lock
        }
    }

    // Method to print the hash table (for debugging purposes)
    public void printTable() {
        for (int i = 0; i < BUCKET_COUNT; i++) {
            Node<K, V> current = buckets[i].next; // Skip the dummy node
            System.out.print("Bucket " + i + ": ");
            while (current != null) {
                System.out.print("(" + current.key + ", " + current.value + ") -> ");
                current = current.next;
            }
            System.out.println("null");
        }
    }

    // Load testing method
    public static void loadTest() throws InterruptedException {
        final ConcurrentHashTable<Integer, String> map = new ConcurrentHashTable<>();
        final int threadCount = 10;
        final int operationsPerThread = 1000;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        Random random = new Random();

        Runnable task = () -> {
            for (int i = 0; i < operationsPerThread; i++) {
                int key = random.nextInt(100); // Random keys between 0 and 99
                int operation = random.nextInt(3); // Random operation (put, get, remove)

                switch (operation) {
                    case 0: // Put operation
                        map.put(key, "Value-" + key);
                        break;
                    case 1: // Get operation
                        map.get(key);
                        break;
                    case 2: // Remove operation
                        map.remove(key);
                        break;
                }
            }
        };

        // Start all threads
        for (int i = 0; i < threadCount; i++) {
            executor.submit(task);
        }

        // Shut down executor and wait for tasks to complete
        Thread.sleep(10000);
        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        // Print the resulting hash table
        System.out.println("Final Hash Table:");
        map.printTable();
    }

    // Main method to run the load test
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting load test...");
        loadTest();
        System.out.println("Load test completed.");
    }
}
