package tpAndNonBlockingAlgo.threadPool;

import tpAndNonBlockingAlgo.periodicTask.Task;

import java.util.*;
import java.util.concurrent.*;

public class ThreadPool {
    private final int numThreads;
    private final List<Thread> threads;

    // only shared resource
    private final BlockingQueue<Runnable> tasksQueue; // BlockingQueue of task; properties of task is run() which has to be run, here object of Runnable will be passed

    private boolean hasShutdown; // non-final as it will change

    private final Thread bookKeeper; // reads through the set of dead thread ids and replaces them with new thread
    // set of dead threads
    private final Set<Integer> deadThreadIds; // whenever a thread is about to die, it would register itself here

    public ThreadPool(int numThreads) {
        this.numThreads = numThreads;
        this.threads = new ArrayList<>();
        this.deadThreadIds = new HashSet<>();

        this.tasksQueue = new ArrayBlockingQueue<>(10); //  not hold more than 10 pending tasks

        for(int i=0; i<numThreads; i++) {
            Thread t = new Thread(new Worker(i, tasksQueue, this.deadThreadIds));
            this.threads.add(t); // threads have just been created and not launched
        }

        for(Thread t: threads) {
            t.start(); // worker threads launched
        }
        this.bookKeeper = new Thread(new BookKeeper(deadThreadIds, threads, tasksQueue));
        this.bookKeeper.start();
    }

    // we can submit some futureTask wrapped around Callable, futureTask can be submitted to Runnable
    // we will accept Callable and return FutureTask from which you can later use get() to return result
    public synchronized FutureTask<Integer> submit(Callable<Integer> callable) throws InterruptedException {
        if(this.hasShutdown) // need synchronized
            throw new RuntimeException("shutting down");

        FutureTask<Integer> ft = new FutureTask<>(callable);
        // ft internally on run calls call() of callable and stores result in wrapper
        // so later if get() call is made, it can procure and return the result
        this.tasksQueue.put(ft);
        return ft;
    }

    // all workers / consumers shutdown gracefully, when shutdown is invoked
    // - workers might be processing something
    // - queue is having items yet to be processed? -> Yes read and then shutdown.
    // - can clients submit new tasks? -> No
    public synchronized void shutDown() throws InterruptedException{ // synchronized as hasShutdown is being used by submit as well
        this.hasShutdown = true;
        for(int i=0; i<numThreads; i++) {
            this.tasksQueue.put(new Shutter());
        }
    }
}
