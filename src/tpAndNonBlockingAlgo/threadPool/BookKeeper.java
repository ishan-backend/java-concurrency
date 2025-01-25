package tpAndNonBlockingAlgo.threadPool;

import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class BookKeeper implements Runnable{
    private final Set<Integer> deadThreadIds;
    private final List<Thread> threads;
    private final BlockingQueue<Runnable> tasksQueue;

    public BookKeeper(Set<Integer> deadThreadIds, List<Thread> threads, BlockingQueue<Runnable> tasksQueue) {
        this.deadThreadIds = deadThreadIds;
        this.threads = threads;
        this.tasksQueue = tasksQueue;
    }

    @Override
    public void run() {
        while(true) {
            // use thread signalling rather than running loop forever
            synchronized (deadThreadIds) {
                while(deadThreadIds.isEmpty()) {
                    try {
                        wait(); // wait till you receive at least one dead thread id, worker will notify this
                    } catch (InterruptedException ie) {
                        throw new RuntimeException(ie);
                    }
                }
            }

            for(Integer i: deadThreadIds) {
                Thread t = new Thread(new Worker(i, tasksQueue, deadThreadIds));
                deadThreadIds.remove(i);
                threads.set(i, t);
                t.start();
            }
        }
    }
}
