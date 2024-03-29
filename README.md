# java-concurrency
All JAVA concurrency constructs with notes/references and implementation
**Concurrency vs. Parallelism, Thread safety**
1. 1.1 Context switches, task scheduling, Thread stack
2. 1.2 Race conditions and Locks
3. 1.3 Reentrant locks and Thread safety measures.

**Volatile, Monitors, Thread Synchronization and signaling**
1. 2.1 Memory visibility issues
2. 2.2 Busy waiting vs wait/notify
3. 2.3 Challenging Exercises, Problem-solving with threads

**Deadlocks, Producer Consumer problem, Dining Philosophers problem**
1. 3.1 Deadlock, Livelock
2. 3.2 Coding Dining Philosophers problem, Producer Consumer problem and its variants

**Explicit Locks, ReadWrite Locks Blocking Queues, Synchronizers**
1. 4.1 Thread safe Data structures, Hand over Hand locking
2. 4.2 Creating our own locks and ensuring reentrancy
3. 4.3 Latch, Semaphore, FutureTask, Barrier

**Thread Pool, Executor Service, Non Blocking Algorithms**
1. 5.1 Building our own thread pool, understanding Executor Service.
2. 5.2 Exercises and Problem-solving, Building a thread-safe hashmap, lock-striping.
3. 5.3 Atomic Data Types

----------------------------------------------------------------------------------------------------------------------------
Java MultiThreading for Senior Engineer Interviews:
The Basics -> Java Memory Model -> Java Concurrency Reference -> MultiThreading in JAVA -> Interview Practice Problems

**Java Concurrency Reference**:
1. Creating Threads:
   * Runnable Interface
     ```text
     We need to tell the thread what task to execute.
     The code can be provided as an object of a class that implements the Runnable interface. As the name implies, the interface forces the implementing class to provide a run method which in turn is invoked by the thread when it starts.

     The runnable interface is the basic abstraction to represent a logical task in Java. 
     ```
   * Subclassing Thread class
     ```text
     The con of the second approach is that one is forced to extend the Thread class which limits code’s flexibility.
     ```
     


  



----------------------------------------------------------------------------------------------------------------------------
* In java, two ways to create a thread:
  * Extend Thread class
  ```java
  // if you extend Thread, you cannot extend any other class by MyThread, JAVA doesn't allow
  // subclass of only one class
  public class MyThread extends Thread {
    public myThread(String threadName) {
      super(threadName);
    }
  
    @Override
    public void run() {
        // write what logic to run
        for(int i=0; i<5; i++) {
            System.out.println(Thread.currentThread().getName()); // currentThread() gives current instance of MyThread
        }
    }
  }
  
  public class MultiThreading {
    public static void main(String[] args) {
        MyThread mtobject = new MyThread("thread1");
        mtobject.start(); // to run() in separate thread apart from Main
    }
  }
  ```
  * Implements runnable interface
  ```java
   public class MyThread implements Runnable {
    @Override
    public void run() {
        // write what logic to run
    }
  }
  
  public class MultiThreading {
    public static void main(String[] args) {
        MyThread mtt = new MyThread();
        Thread mt = new Thread(mtt); // mtt is Runnable
        // Main is parent thread and mt is child thread
        // up to mercy of JVM to run
        mt.setDaemon(true); // mt is a daemon thread, if main or all other user threads stops executing, mt might not continue working
        mt.start(); // to run() in separate thread apart from Main, there is some time gap between calling start() and actual run() by JVM internally
        mt.join(); // stop executing the Main thread until mt completes
        mt.isAlive(); // check if mt is still alive (not completed)
  
  
  
        // using Lambda's
        Thread mt2 = new Thread(()->{
            // body of run() method being passed as parameter
            for(int i=0; i<5; i++) {
                System.out.println(i);
            }
        }).start();
    }
  }
  ```
  
* Synchronization and its importance:
  - Threads share same memory space i.e. they can share resource (object)
  - Desirable situation - only one thread should access a shared resource at a time
    - e.g. BookMyShow - remainingSeats
      - avoid race condition -> where one thread reads and books some seat
      - meanwhile other thread read old data and also called book() but till now first thread has already booked
  - e.g. Design Stack being accessed by multiple threads
  - How to synchronize static methods?
    - we synchronize on the class lock, since static means there is no object so no concept of this
    - Singleton pattern e.g.
    ```java
    public class TVSet {
        private static volatile TVSet instance = null;
        public TVSet() {
            System.out.println("tv set instantiated");
        }   
    
        public static TVSet getInstance() {
            if(instance == null) {
                synchronized (TVSet.class) {
                    if(instance == null) { // double checking
                        instance = new TVSet();
                    }
                }
            }
            return instance;
        }
        
        public static synchronized boolean doWork() {
            // in static synchronized methods, behind the scenes synchronized(TVSet.class)
            return true;
        }
    }
    ```
    Note: Non-Synchronized methods can be called by any thread at any point in time.
  - **Race Condition:**
    - When two or more threads try to access/update same index in stack, leaving it in undefined/inconsistent state.

**Thread State Transitions**:
![thread states](./Screenshot 2024-03-17 at 10.59.16 AM.png)

![thread transitions](./Screenshot 2024-03-17 at 11.03.22 AM.png)

![running and yielding](./Screenshot 2024-03-17 at 11.19.47 AM.png)
  * yield() is an advisory method (not guaranteed immediately) to JVM to move thread from running to ready-to-run state.

![sleep and interrupted exception](./Screenshot 2024-03-17 at 11.22.50 AM.png)
  * whenever we are calling sleep() method we need to enclose it in interrupted exception, whenever it gets chance to execute/run it will throw interrupted exception.
  * when thread is sleeping, it does not let go of lock

![waiting and notifying](./Screenshot 2024-03-17 at 11.24.41 AM.png)
  * when thread is waiting, it lets go of lock
  * thread only relinquishes lock on object on which wait method was invoked
  * threads waiting for notify from object are grouped, because those threads use object.wait()
