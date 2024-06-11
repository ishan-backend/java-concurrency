package synchronization.countDownLatch;

// synchronized - to avoid race condition on shared variable and to not cause memory visibility issues
public class OverallSum {
    private int val;
    public OverallSum() {
        this.val = val;
    }

    public synchronized void add(int x) {
        val += x;
    }

    public synchronized int get() {
        return val;
    }
}
