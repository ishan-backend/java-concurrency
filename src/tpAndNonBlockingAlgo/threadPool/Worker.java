package tpAndNonBlockingAlgo.threadPool;

import java.util.concurrent.BlockingQueue;

// a thread can be injected using only one runnable instance
public class Worker implements Runnable{
    private final int id;
    // queue of runnable objects
    private final BlockingQueue<Runnable> tasksQueue; // tasksQueue reference in Worker / consumer

    public Worker(int id, BlockingQueue<Runnable> tasksQueue) {
        this.id = id;
        this.tasksQueue = tasksQueue;
    }

    @Override
    public void run() {
        while(true) {
            Runnable task = null;
            // run method of worker deals with multiple runnables
            try {
                task = this.tasksQueue.take(); // retrieves and returns head of queue, waiting if necessary until an element becomes available
                task.run();
            } catch (Exception e) {
                // on run of last message, shutter will initiate
                if("shutdown initiated".equals(e.getMessage())) {
                    break;
                }
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Thread# " + id + " exiting.");
    }
}
