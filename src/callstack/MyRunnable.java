package callstack;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("running txn ...");
        throw new RuntimeException();
    }
}
