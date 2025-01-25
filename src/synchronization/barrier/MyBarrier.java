package synchronization.barrier;

public class MyBarrier {
    private final int numThreads; // no of threads participating in barrier
    private int count, exits; // this variable is changed - until all x threads have arrived at this Barrier point, we should not start our next action
    // exits: how many threads have exited so far

    // action : a runnable logic
    // runnable interface implementation contains a method run (inside logic can be made to run)

    private final Runnable barrierTask; // pass object of a class that implements Runnable interface
    // here you are just passing an object, not a thread (note: a thread spawns different stack)
    // run() is used in two cases: just pass a logic, or create a thread

    public MyBarrier(int numThreads, Runnable barrierTask) {
        this.barrierTask = barrierTask;
        this.count = numThreads;
        this.numThreads = numThreads;
        this.exits = 0;
    }



    // a thread when finishes its task, calls await()
    public synchronized void await() throws InterruptedException{
        count--;
        if(count > 0) {
            while(count > 0) // a thread() may again come live due to Spurious wakeups (handling that case)
                wait(); // a thread has to wait for other threads to arrive to the Barrier
        } else {
            // run barrier task - run by last thread which reaches here (makes count = 0)
            barrierTask.run();
            //signal all the waiting threads
            notifyAll();

            // value of count should be reset only when all other threads which are waiting inside while() loop exit
        }

        exits++;
        if(exits == numThreads) {
            // reset block
            count = numThreads;
            exits = 0;
        }
    }

    // In JAVA, barrier is CyclicBarrier (after each round, it cycles back)
    // it takes care of other edge cases:
    // when thread goes to wait(), at times there are SpuriousWakeups (might wake up without being explicitly notified. This can occur due to various reasons, including optimizations within the operating system or hardware, or other internal conditions. This means a thread could resume execution from its waiting state even if no other thread has signaled the condition variable.)
}
