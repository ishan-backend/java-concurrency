**Synchronizers**:

* JAVA provides synchronization 
  1. contain some state variables inside (variable, maps, etc)
  2. logic to manipulate or change the synchronization variables
  3. offers way for thread to wait
  4. issue notification to other threads
* Frequently used synchronizers:
  1. Latch:
     * to close a door
     * if you have a thread, whose work is dependant on other threads
     * you want this thread to wait until dependant threads have done their jobs
     * those threads then can release the latch, and waiting thread can continue its work
     * e.g. CountDownLatch
     * we can implement our own version of CountDownLatch
     * Use cases:
       * Orchestrator service, reader service, writer service
         * Ensure that orchestrator service is only up when downstream service (reader, writer) are up
  2. Semaphore:
     * most important synchronizer
     * called controlling synchronizer, wide variety of applications
     * Thread has to acquire permit from count, then do work and then release permit to count
     * Difference between Semaphore and Mutual Exclusion Lock:
       * In ME Lock, only one permit was allowed
       * Here, X permits are allowed and X is configurable
     * **Important**:
       * Semaphore class itself only tracks count variable (that is its only responsibility, it does not track threads)
       * Some other class will make use of this Semaphore class to grant objects out of object pool
       * **IMP**: Thread T can acquire a permit p1, even if some other Thread t2 makes release of this p1, it does not matter.
     * Use cases:
       * Connection Pools (set of connections):
         * thread in application server, pickup object do the work, and return object back to pool
         * race conditions in this picking up object from connection pool
         * semaphore helps here, semaphore maintains permits - any thread can acquire the permit, do work and release the permit
       * Third party APIs:
         * underlying concept of semaphore/permits to build connection pool
       * Ensure only 3 concurrent writers (configurable) to a file:
         * Can be 100 writer threads, but at a time only 3 threads can write
  3. Barrier (rokna):
     * Non-deterministic problem (pata ni kab khtm hoga)
     * Broken problem to 10 threads - the threads do their job independently and concurrently.
     * After the completion of tasks (all threads have finished) (inspection), we try to merge back the results. But individual thread has to wait till all threads have finished. 
     * If inspection (expected answer matching) fails, we might try to re-run those jobs (schedule them again). Else, we will terminate.
  4. Blocking Queue:
     * Earlier, we had 2 types of queues - normal and shared queue
     * Shared queue - normal producer-consumer problem
       * it would work well, if only 1 thread is operating on it for time being
       * But, we have 2 clients that use this DS - producer & consumer
       * Since, shared queue DS was not synchronized, so synchronization logic was held by clients
         * wait() and notify() contained in producer and consumer
     * If we don't want above i.e. wait() and notify() contained in client, we can use BlockingQueue (synchronized DS/collection contains it)
     * Producer - draft message and send it
     * Consumer - consuming message and take business action
     * 