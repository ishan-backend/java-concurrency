package tpAndNonBlockingAlgo.threadPool;

import tpAndNonBlockingAlgo.pairCounterBeforeThreadPool.PairCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadPool threadPool = new ThreadPool(3);
        List<FutureTask<Integer>> ftList = new ArrayList<>();
        Random random = new Random();


        int k = 0;
        // Client side / producer simulation
        for(int i=0; i<20; i++) { // submit tasks to threadPool
            FutureTask<Integer> res = null;
            if(i%2 == 0) {
                int qty = 30000;
                List<Integer> nums = new ArrayList<>();
                for(int j=0; j<qty; j++) {
                    nums.add(random.nextInt(100, 200));
                }
                int sum = random.nextInt(200, 400);
                res = threadPool.submit(new PairCounter(nums, sum));

                // Add the FutureTask to the list for later retrieval
                ftList.add(res);
            } else {
                // different client submit different callables
                // some other task / callable
            }
        }

        threadPool.shutDown();
        for (FutureTask<Integer> ft : ftList) {
            Integer ans = ft.get(); // Blocking call to get the result
            System.out.println(ans);
        }
    }
}
