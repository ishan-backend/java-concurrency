package synchronization.countDownLatch;

import java.util.ArrayList;
import java.util.List;

public class SumOfList {
    public static void main(String[] args) {
        /*
        * Given an array, task is to sum this array in parts by different threads
        * a separate thread to add these sums and print it
        */

        OverallSum overallSum = new OverallSum();
        CountDownLatch countDownLatch = new CountDownLatch(4);
        List<Integer> nums = new ArrayList<>();
        for(int i=0; i<100; i++) {
            nums.add(i);
        }

        int sz = 25;
        CountDownLatch getSetGo = new CountDownLatch(1);
        // pass it in all the workers
        Thread t1 = new Thread(new Adder(0, sz-1, nums, countDownLatch, overallSum, getSetGo));
        Thread t2 = new Thread(new Adder(sz, 2*sz-1, nums, countDownLatch, overallSum, getSetGo));
        Thread t3 = new Thread(new Adder(2*sz, 3*sz-1, nums, countDownLatch, overallSum, getSetGo));
        Thread t4 = new Thread(new Adder(3*sz, 4*sz-1, nums, countDownLatch, overallSum, getSetGo));

        Thread g1 = new Thread(new Getter(overallSum, countDownLatch));

        /*
            g1.start();
            t1.start();
            t2.start();
            t3.start();
            t4.start();

            Actually all threads here are not fired at same time, its sequential in nature.
            U can use latch to fire all these threads at the same time, main thread's will be to do so.
         */

        g1.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        getSetGo.countDown();
    }
}
