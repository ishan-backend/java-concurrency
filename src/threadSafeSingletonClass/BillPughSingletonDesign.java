package threadSafeSingletonClass;

// It leverages the class loader mechanism to ensure thread safety and lazy initialization without the need for explicit synchronization
public class BillPughSingletonDesign {

    // private constructor to prevent instantiation
    private BillPughSingletonDesign() {
        // Prevent instantiation via reflection
        if (SingletonHolder.INSTANCE != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    // Inner static class responsible for holding the BillPughSingletonDesign instance
    // The SingletonHolder class is not loaded until it is referenced, which happens when the getInstance method is called.
    private static class SingletonHolder {
        private static final BillPughSingletonDesign INSTANCE = new BillPughSingletonDesign(); // is a static final variable, which ensures that it is initialized only once and in a thread-safe manner.
    }

    // Public method to provide access to the Singleton instance
    public static BillPughSingletonDesign getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
