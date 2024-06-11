package threadSynchronization.readWriteLock;

public class Writer implements Runnable {
    private final DataStore dataStore;
    public Writer(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public void run() {
        try {
            dataStore.write();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Writer wrote!");
    }
}
