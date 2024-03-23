package interview_practice_problems.implementing_semaphores;

/*
    A semaphore is a construct that allows some threads to access a fixed set of resources/permits in parallel.
    Once all the permits are given out, requesting threads, need to wait for a permit to be returned before proceeding forward.
    Remember that semaphores solve the problem of missed signals between cooperating threads.
    There is no concept of ownership for a semaphore ! Hence different threads can call acquire or release methods as they deem fit.

    Java does provide its own implementation of Semaphore,
    however, Java's semaphore is initialized with an initial number of permits, rather than the maximum possible permits
    and the developer is expected to take care of always releasing the intended number of maximum permits.

    acquire() function to simulate gaining a permit
    release() function to simulate releasing a permit

    How to ensure usedPermits variable is correctly incremented or decremented ?
    -> synchronized keyword to both the class methods - causes only a single thread to execute either of the methods.
    -> If a thread is currently executing acquire() then another thread can't execute release() on the same semaphore object.
*/
public class CountingSemaphore {
    int usedPermits = 0;
    int maxPermits = 0;

    public CountingSemaphore(int maxPermits) {
        this.maxPermits = maxPermits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (usedPermits == maxPermits)
            wait(); // thread cannot be allowed to acquire a semaphore

        usedPermits++;
        notify(); // This also means that whenever we decrement or increment the usedPermits variable we need to call notify() so that any waiting thread in the other method is able to move forward.
    }

    public synchronized void release() throws InterruptedException {
        // counter-intuitive, you might ask why would someone call release() before calling acquire() -
        // This is entirely possible since semaphore can also be used for signalling between threads.
        // A thread can call release() on a semaphore object before another thread calls acquire() on the same semaphore object.
        while(usedPermits == 0)
            wait();

        usedPermits--;
        notify();
    }
}
