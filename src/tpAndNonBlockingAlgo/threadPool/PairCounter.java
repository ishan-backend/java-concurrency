package tpAndNonBlockingAlgo.threadPool;

import java.util.List;
import java.util.concurrent.Callable;

public class PairCounter implements Callable<Integer> {
    private final List<Integer> nums;
    private final int sum;

    public PairCounter(List<Integer> nums, int sum) {
        this.nums = nums;
        this.sum = sum;
    }

    @Override
    public Integer call() throws Exception {
        int cnt = 0;
        for(int i=0; i<nums.size(); i++) {
            for(int j=i+1; j<nums.size(); j++) {
                if((nums.get(i) + nums.get(j)) == sum) {
                    cnt++;
                }
            }
        }

        return cnt;
    }
}
