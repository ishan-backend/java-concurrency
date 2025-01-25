package synchronization.semaphore;

public class CharPrinter implements Runnable{
    private final char ch;
    // reference to Semaphore
    private final Semaphore semaphore;

    public CharPrinter(char ch, Semaphore semaphore) {
        this.ch = ch;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        // even after we acquire the semaphore
        // we have kept other code in try catch finally
        // because we want to make sure that in case of any exception, we release the semaphore acquired
        try {
            // allow access to my critical section to only x threads at a time
            for(int i=0; i<10; i++) {
                System.out.print(ch);
                Thread.sleep(50);
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            semaphore.release();
        }
    }
}
