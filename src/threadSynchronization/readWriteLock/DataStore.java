package threadSynchronization.readWriteLock;

public class DataStore {
    private volatile String name; // volatile - memory visibility to all threads
    private final ReadWriteLock lock; // reference to lock

    public DataStore(ReadWriteLock lock) {
        this.lock = lock;
        this.name = "some string";
    }

    public String read() throws InterruptedException {
        lock.lockRead();
        try {
            return name;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlockRead();
        }
    }

    public void write() throws InterruptedException {
        lock.lockWrite(); // synchronized block ends by the time value is returned
        try {
            name += "some extra string"; // so, changes made here, may not be visible to other threads
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlockWrite();
        }
    }
}
