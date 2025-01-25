package tpAndNonBlockingAlgo.threadPool;

import java.util.concurrent.Callable;

public class Thrower implements Callable {
    @Override
    public Object call() {
        throw new RuntimeException("throw initiated");
    }
}

