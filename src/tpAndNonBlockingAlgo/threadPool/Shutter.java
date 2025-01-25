package tpAndNonBlockingAlgo.threadPool;

public class Shutter implements Runnable{
    @Override
    public void run() {
        throw new RuntimeException("shutdown initiated");
    }
}
