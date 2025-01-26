package tpAndNonBlockingAlgo.executorService;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Worker w1 = new Worker("1");
        Worker w2 = new Worker("2");
        Worker w3 = new Worker("3");
        Worker w4 = new Worker("4");
        Worker w5 = new Worker("5");
        Worker w6 = new Worker("6");
        executorService.submit(w1);
        Future<?> ft = executorService.submit(w2);
        executorService.submit(w3);
        executorService.submit(w5);
        executorService.submit(w4);
        executorService.submit(w6);

        ft.get();

        executorService.shutdown();
    }
}
