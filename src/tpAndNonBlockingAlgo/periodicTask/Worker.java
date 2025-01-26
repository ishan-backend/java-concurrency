package tpAndNonBlockingAlgo.periodicTask;

import java.util.concurrent.BlockingQueue;

// a thread can be injected using only one runnable instance
public class Worker implements Runnable{
    private final int id;
    private final BlockingQueue<Task> tasksQueue;

    public Worker(int id, BlockingQueue<Task> tasksQueue) {
        this.id = id;
        this.tasksQueue = tasksQueue;
    }

    @Override
    public void run() {
        while(true) {
            Task task = null;
            try {
                task = this.tasksQueue.take();
                long currTime = System.currentTimeMillis();
                if(currTime >= task.getFireTime()) {
                    task.run();
                    if(task.getSubsequentGapInSecs() > 0) {
                        task.setFireTime(System.currentTimeMillis() + task.getSubsequentGapInSecs()*1000L);
                        this.tasksQueue.put(task);
                    }
                } else {
                    this.tasksQueue.put(task);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
