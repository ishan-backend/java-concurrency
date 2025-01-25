package tpAndNonBlockingAlgo.pairCounterBeforeThreadPool;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Server s = new Server();
        Random random = new Random();

        Map<Integer, FutureTask<Integer>> m = new HashMap<>();
        long start = System.currentTimeMillis();
         for(int i=0; i<5; i++) {
             int qty = 30000;
             List<Integer> numsList = new ArrayList<>();
             for(int j=0; j<qty; j++) {
                 numsList.add(random.nextInt(100, 200));
             }

             int sum = random.nextInt(200, 400);
             FutureTask<Integer> ft = s.getPairCount(numsList, sum);
             m.put(i, ft);
         }

         long end = System.currentTimeMillis();
         System.out.println("Total time: " + (end - start));
         for(int i=0; i<5; i++) {
             System.out.println(" Req # " + i + " : " + m.get(i).get());
         }
    }
}
