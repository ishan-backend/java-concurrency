package synchronization.countDownLatch;

// both methods - await() and countDown() are synchronized as they are tampering with shared variable
public class CountDownLatch {
    private int count;
    public CountDownLatch(int count) {
        this.count = count;
    }

    public synchronized void await() throws InterruptedException {
        while(count > 0) {
            System.out.println(Thread.currentThread().getId() + " waiting.");
            wait();
        }

        System.out.println(Thread.currentThread().getId() + " exiting await.");
    }

    public synchronized void countDown() {
        count--;
        if(count == 0)
            notifyAll(); // issued to all waiting threads

        System.out.println(Thread.currentThread().getId() + " decreased the count.");
    }
}
