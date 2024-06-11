package threadSafeSingletonClass;

public class DoubleCheckedLockingSingleton {
    // volatile keyword ensures that multiple threads handle the instance variable correctly
    // it ensures that the instance variable is read directly from the main memory and not from the thread's local cache.
    private static volatile DoubleCheckedLockingSingleton instance;

    // private constructor to prevent instantiation
    private DoubleCheckedLockingSingleton() {
        // Prevent instantiation via reflection
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static DoubleCheckedLockingSingleton getInstance() {
        if (instance == null) { // First check (no locking)
            synchronized (DoubleCheckedLockingSingleton.class) { //  ensuring that only one thread can execute this block at a time.
                if (instance == null) { // Second check (with locking)
                    instance = new DoubleCheckedLockingSingleton();
                }
            }
        }

        // subsequent calls to getInstance do not incur the overhead of synchronization
        return instance;
    }
}
