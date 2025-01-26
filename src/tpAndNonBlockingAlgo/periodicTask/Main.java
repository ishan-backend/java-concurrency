package tpAndNonBlockingAlgo.periodicTask;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(3);
        Task t1 = new Task("Message 1", 1, 5);
        Task t2 = new Task("Message 2", 1, 10);
        Task t3 = new Task("Message 3", 1, 8);

        threadPool.submit(t1);
        threadPool.submit(t2);
        threadPool.submit(t3);
    }
}
