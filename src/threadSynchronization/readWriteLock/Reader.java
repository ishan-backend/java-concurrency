package threadSynchronization.readWriteLock;

public class Reader implements Runnable {
    private final DataStore dataStore;
    public Reader(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void run() {
        String name = null;
        try {
            name = dataStore.read();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Read the data: "+ name);
    }
}
