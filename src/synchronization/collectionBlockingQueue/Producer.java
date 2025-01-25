package synchronization.collectionBlockingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

// producer code no where has thread signalling
public class Producer implements Runnable{
    private final Random random;
    private final BlockingQueue<Integer> blockingQueue;

    public Producer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
        this.random = new Random();
    }

    @Override
    public void run() {
        for(int i=0; i<25; i++) {
            try {
                int val = random.nextInt(10, 100);
                // before put we don't need to worry if queue is full or not, because that logic is already inside put() method
                blockingQueue.put(val);
                // put acquires lock by lock.putInterupptably so we handle InterruptedException
                // due to context switch the print statement for consumer may come before producer
                System.out.println(Thread.currentThread().getId() + " inserted:" + i);
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        }

        // last item inserted is -1
        try {
            blockingQueue.put(-1);
        } catch(InterruptedException ie) {
            throw new RuntimeException(ie);
        }
    }
}
