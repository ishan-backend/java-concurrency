package tpAndNonBlockingAlgo.periodicTask;


import java.util.*;
import java.util.concurrent.*;

public class ThreadPool {
    private final int numThreads;
    private final List<Thread> threads;
    private final BlockingQueue<Task> tasksQueue;

    public ThreadPool(int numThreads) {
        this.numThreads = numThreads;
        this.tasksQueue = new PriorityBlockingQueue<>(10, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                long diff = o1.getFireTime() - o2.getFireTime();
                int ans = 0;
                if(diff > 0)
                    ans = 1;
                if(diff < 0)
                    ans = -1;
                return ans;
            }
        });

        this.threads = new ArrayList<>();
        for(int i=0; i<numThreads; i++) {
            Thread t = new Thread(new Worker(i, tasksQueue));
            this.threads.add(t); // threads have just been created and not launched
        }

        for(Thread t: threads) {
            t.start(); // worker threads launched
        }
    }

    public synchronized void submit(Task t) throws InterruptedException {
        this.tasksQueue.put(t);
    }
}
