package synchronization.futures;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureReturnRunnable future1 = new FutureReturnRunnable();
        new Thread(future1).start(); // thread can invoke run() on object your are passing since future1 class implements run() method
        System.out.println("started...");
        String ans = future1.get();
        System.out.println(ans);

        FutureTask<String> ft = new FutureTask<>(new MyCallable());
        new Thread(ft).start(); // invokes run inside this runnable, which in turn invokes call() method of callable passed within this wrapper
        System.out.println("started...");
        String ans1 = ft.get();
        System.out.println(ans1);
    }
}
