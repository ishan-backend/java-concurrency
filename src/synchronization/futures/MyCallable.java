package synchronization.futures;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        return "Hello this is result";
    }
}
