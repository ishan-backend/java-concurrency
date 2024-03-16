package parallelism;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/*
* (Split sequential flow into components)
* Tools to enable parallelism and thus speed up:
* - Threads
* - ThreadPool
*   - ExecutorService
*   - ForkJoinPool
*   - Custom ThreadPools
* - Requires > 1 CPU cores
*
*
* Thread scheduling is not in developers control
* Which instructions are being run by a thread is also non-deterministic
*
*
* (Whenever shared resource is to be updated, use concurrency tools to manage data)
* (Whenever independent components running on threads need to coordinate, use concurrency tools)
* Tools to deal with concurrency:
* - Locks / synchronized
* - atomic classes
* - Concurrent data structures (ConcurrentHashmap, BlockingQueue)
* - Completable Future
* - CountdownLatch / Phaser / Cyclic Barrier / Semaphore etc
* */
public class Driver {
    public static void main(String[] args) {
        /*
        * Parallelism - using Threads and ThreadPool without any shared variable
        * */
        // task1 - parallelism (our own thread)
        new Thread(new Runnable() {
            @Override
            public void run() {
                // processTask(user1);
            }
        }).start();

        // task2 - parallelism
        new Thread(()->{
            // processTask(user2);
        }).start();

        ExecutorService es = Executors.newFixedThreadPool(4);
        es.submit(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task1");
        });
        es.submit(()->{System.out.println("task2");});

        /*
        * With shared variable, comes problem of concurrency
        * Solution:
        *   - using locks
        * */
        ThreadSafeByMutexLock threadSafeByMutexLock = new ThreadSafeByMutexLock();
        Thread t1 = new Thread(threadSafeByMutexLock, "t1");
        Thread t2 = new Thread(threadSafeByMutexLock, "t2");
        Thread t3 = new Thread(threadSafeByMutexLock, "t3");
        Thread t4 = new Thread(threadSafeByMutexLock, "t4");
        Thread t5 = new Thread(threadSafeByMutexLock, "t5");
        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }
}

class ThreadSafeByMutexLock implements Runnable {
    int count = 5;
    ReentrantLock lock = new ReentrantLock();
    @Override
    public void run() {
        try {
            lock.lock();
            count--;
            System.out.println(Thread.currentThread().getName() + " count: " + count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}