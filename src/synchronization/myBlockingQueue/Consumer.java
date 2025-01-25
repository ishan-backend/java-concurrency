package synchronization.myBlockingQueue;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{
    private final MyBlockingQueue blockingQueue;
    public Consumer(MyBlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while(true) {
            int val;
            try {
                // take() contains all logic if queue is full or not
                // no underflow or overflow
                val = blockingQueue.take();
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }

            System.out.println(Thread.currentThread().getId() + " consumed: " + val);
            if(val == -1) {
                break;
            }
        }
    }
}
