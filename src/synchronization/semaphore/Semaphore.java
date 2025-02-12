package synchronization.semaphore;

public class Semaphore {
    private int permitsLeft;
    private final int maxPermits;

    public Semaphore(int maxPermits) {
        if(maxPermits < 0)
            throw new RuntimeException("maxPermits < 0");
        this.maxPermits = maxPermits;
        this.permitsLeft = maxPermits;
    }

    public synchronized void acquire() throws InterruptedException{
        while(permitsLeft == 0)
            wait();

        permitsLeft--;
    }

    public synchronized void release(){
        while(permitsLeft == maxPermits)
            throw new RuntimeException("illegal release");

        permitsLeft++;
        if(permitsLeft == 1)
            notifyAll();
    }
}
