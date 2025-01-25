package synchronization.futures;

public class FutureReturnRunnable implements Runnable{
    private String output;
    private boolean isDone;

    public FutureReturnRunnable() {
        this.isDone = false;
    }

    @Override
    public void run() {
        synchronized (this) { // acquire lock on this object, synchron. is needed as there is shared variable getting manipulated isDone
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }

            output = "Result";
            isDone = true;
        }
    }

    public synchronized String get() throws InterruptedException {
        while(!isDone) {
            wait();
        }

        return output;
    }
}
