package tpAndNonBlockingAlgo.executorService;

public class Worker implements Runnable{
    private final String message;
    public Worker(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("print message" + this.message);
    }
}
