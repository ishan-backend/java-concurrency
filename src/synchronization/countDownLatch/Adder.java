package synchronization.countDownLatch;

import java.util.List;

public class Adder implements Runnable{
    private int startIndex, endIndex;
    private final List<Integer> nums;
    private final CountDownLatch countDownLatch; // thread synchronization
    private final OverallSum overallSum; // to finally add this parts sum to ans

    private final CountDownLatch getSetGo; // to fire all workers at same time from main thread

    public Adder(int startIndex, int endIndex, List<Integer> nums, CountDownLatch countDownLatch, OverallSum overallSum, CountDownLatch getSetGo) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.nums = nums;
        this.countDownLatch = countDownLatch;
        this.overallSum = overallSum;
        this.getSetGo = getSetGo;
    }

    @Override
    public void run() {
        try {
            getSetGo.await();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        int a = 0;
        for(int i=startIndex; i<endIndex; i++) {
            a += nums.get(i);
        }
        overallSum.add(a);
        countDownLatch.countDown(); // at some time, this will cause the value stored inside countDownLatch to be 0
    }
}
