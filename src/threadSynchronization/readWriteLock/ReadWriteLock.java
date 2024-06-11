package threadSynchronization.readWriteLock;

// Why all methods are synchronized?
//    - There are some shared variables we are tampering
//    - When a certain method calls lockRead() we do not want any other method to call lockRead()

// We will add things on top of existing synchronized, adding some intended behaviours
public class ReadWriteLock {
    private int readers, writers;

    // try to give more priority to write operation
    private int writeReqCount;

    public ReadWriteLock() {
        this.readers = 0;
        this.writers = 0;
        this.writeReqCount = 0;
    }

    public synchronized void lockRead() throws InterruptedException {
        while(writers > 0 || writeReqCount > 0) // if any existing writers or pending write requests. Keep readers waiting, until write happens.
            wait();

        readers++;
    }

    public synchronized void unlockRead() {
        readers--;
        notifyAll(); // to all waiting threads (readers or writers -> may lead to starvation of writers)
    }

    public synchronized void lockWrite() throws InterruptedException { // request for write has arrived
        writeReqCount++;
        while (readers > 0 || writers > 0) // if any existing reader or writer, wait(). New write request is added to writeReqCount.
            wait();

        writeReqCount--; // if write request has been entertained, do --
        writers++;
    }

    public synchronized void unlockWrite() {
        writers--;
        notifyAll();
    }
}
