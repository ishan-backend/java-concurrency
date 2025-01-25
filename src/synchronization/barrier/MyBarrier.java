package synchronization.barrier;

public class MyBarrier {
    private final int numThreads;
    private int count, exits; // this variable is changed - until all x threads have arrived at this Barrier point, we should not start our next action
    // exits: how many threads have exited so far

    // action : a runnable logic which Runnable interface helps run
    // runnable interface implementation contains a method run (inside logic can be made to run)
    private final Runnable barrierTask;  // here you are just passing an object (of a class that implements Runnable interface), not a thread (note: a thread spawns different stack)
    // Runnable can be used to pass both object / thread

    // boolean to check if we are ready or not
    private boolean isReady;

    public MyBarrier(int numThreads, Runnable barrierTask) {
        this.barrierTask = barrierTask;
        this.count = numThreads;
        this.numThreads = numThreads;
        this.exits = 0;
        this.isReady = true;
    }

    // a thread when finishes its task, calls await()
    public synchronized void await() throws InterruptedException{
        while(!isReady)
            wait(); // cyclic barrier cannot run again unless all threads have passed previous barrier


        count--;
        if(count > 0) {
            while(count > 0) // a thread() may again come live due to Spurious wakeups (handling that case)
                wait(); // a thread has to wait for other threads to arrive to the Barrier
        } else {
            // run barrier task - run by last thread which reaches here (makes count = 0)
            barrierTask.run();

            // after last thread runs await() logic, make sure any other thread does not run await logic
            // as long as all threads have exited and make sure that barrier is passed
            isReady = false;

            //signal all the waiting threads
            notifyAll();

            // value of count should be reset only when all other threads which are waiting inside while() loop exit
        }

        exits++; // counts no of threads which have exited wait() / barrier
        if(exits == numThreads) {
            // reset block
            count = numThreads;
            exits = 0;
            isReady = true;
            notifyAll(); // make sure other threads can then pass first wait() to enter to cyclic barrier
        }
    }

    // In JAVA, barrier is CyclicBarrier (after each round, it cycles back)
    // it takes care of other edge cases:
    // when thread goes to wait(), at times there are SpuriousWakeups (might wake up without being explicitly notified. This can occur due to various reasons, including optimizations within the operating system or hardware, or other internal conditions. This means a thread could resume execution from its waiting state even if no other thread has signaled the condition variable.)
}
