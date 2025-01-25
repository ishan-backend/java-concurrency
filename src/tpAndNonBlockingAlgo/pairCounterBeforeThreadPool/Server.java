package tpAndNonBlockingAlgo.pairCounterBeforeThreadPool;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Server {
    // to increase responsiveness - asynchronous
    public FutureTask<Integer> getPairCount(List<Integer> nums, int sum) {
        Callable<Integer> callable = new PairCounter(nums, sum);
        FutureTask<Integer> ft = new FutureTask<>(callable);
        new Thread(ft).start();
        return ft;
    }
}
