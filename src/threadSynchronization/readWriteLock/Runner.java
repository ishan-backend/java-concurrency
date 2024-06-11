package threadSynchronization.readWriteLock;

public class Runner {
    public static void main(String[] args) {
        ReadWriteLock rwLock = new ReadWriteLock();
        DataStore store = new DataStore(rwLock);

        Thread t1 = new Thread(new Reader(store));
        Thread t2 = new Thread(new Reader(store));
        Thread t3 = new Thread(new Reader(store));
        Thread t4 = new Thread(new Reader(store));
        Thread t5 = new Thread(new Reader(store));

        Thread t6 = new Thread(new Writer(store));

        t1.start();t2.start();t3.start();
        t6.start();
        t4.start();t5.start();
    }
}
