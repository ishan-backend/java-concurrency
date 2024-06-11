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
  2. 