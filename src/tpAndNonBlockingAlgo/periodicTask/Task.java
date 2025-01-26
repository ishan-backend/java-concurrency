package tpAndNonBlockingAlgo.periodicTask;

// Task job is to print message
public class Task implements Runnable {

    private final String message;
    // multi-shot task
    private final int initialGapInSecs;
    private final int subsequentGapInSecs;
    // Fire time: single-shot task
    private long fireTime;

    public Task(String message, int initialGapInSecs, int subsequentGapInSecs) {
        this.message = message;
        this.initialGapInSecs = initialGapInSecs;
        this.subsequentGapInSecs = subsequentGapInSecs;
        this.fireTime = System.currentTimeMillis() + initialGapInSecs * 1000L; // ms
    }

    // Getter for initialGapInSecs
    public int getInitialGapInSecs() {
        return initialGapInSecs;
    }

    // Getter for subsequentGapInSecs
    public int getSubsequentGapInSecs() {
        return subsequentGapInSecs;
    }

    // Getter for fireTime
    public long getFireTime() {
        return fireTime;
    }

    // Setter for fireTime
    public void setFireTime(long fireTime) {
        this.fireTime = fireTime;
    }

    @Override
    public void run() {
        System.out.println(message);
    }
}
