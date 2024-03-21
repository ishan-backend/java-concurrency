package thread_safe_task_scheduler;

public class TaskScheduler<E> {
    private NonBlockingQueue<E> tasks;

    public TaskScheduler() {
        this.tasks = new NonBlockingQueue<E>();
    }

    public void addTask(E task) {
        tasks.add(E);
    }

    public synchronized void waitUntilComplete() throws InterruptedException {
        while (tasks.poll())
            wait();

        System.out.println("Done");
    }
}
