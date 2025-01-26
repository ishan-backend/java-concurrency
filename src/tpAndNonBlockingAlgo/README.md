1. Thread Pools (on top of futures)
   * Ex. PairCounter Class that takes integers and a sum, returns count of pairs in list whose sum matches input sum
     * use futureTask to create async threads, similar to web server serving requests
   * To handle things in real-world:
     * Every request would spawn FutureRequest
       * It can bring entire system down, thread is an object which occupies space in memory
       * CPU load increases as thread is spawned
       * Cant spawn unlimited no of threads - drains system of resources
       * Systems impose limit on maximum no of threads allowed to create
     * CPU Usage, Disk limit on server
     * Pre-processing / time is incurred on creating a thread object
       * then task is assigned
       * then CPU is scheduled
       * when task is done, thread is torn down - GC that also takes time + cost
     * So, don't create threads on every new request
     
   * IDEAL SOLUTION:
     * x at max defined number of threads already = thread pool
     * put requests in request queue, as requests are coming
     * thread from pool picks up request and executes it
     * System would be having pre-defined load (to handle 15 max requests)
     
   * What exactly is independent task?
     * State of every task should have no dependency, which will be handled by a particular thread
     * For concurrent systems to give you best results, Amount of work undergone by tasks should not vary much (should be homogeneous)
   
   * ThreadPool class:
     * fixed num of working threads i.e workers (reduces pressure on CPU)
     * task queue (queue can grow out of size, so memory OOM -> queue of pre-defined capacity -> client putting request will have to wait if queue is full)
       * queue will have many clients, put and pick from this queue
       * synchronized queue
       * workers will wait if queue is empty
       * Collection pre-built -> BlockingQueue of Tasks
       * Separate/Decouple Task Submission (to queue) and Task Execution (when and how)
       * 
     * every worker thread would have run() method, pick task from queue and run it, keep on repeating it
     * Public method submit() enqueues task to queue
     * When a thread is launched, its run() is called, if run() exits, then thread is GC and removed from system.
       * we dont want this for our workers
       * we want threads that run for infinity
         * Write your own runnable implementation
       * what if worker thread dies?
       * 
     * JVM will exit threadpool, when all threads are shut down. Have an explicit shutdown method as well.
       * program needs to shutdown threadpool before exiting.
       * terminate threads gracefully.
     * Handling thread deaths:
       * shear unresponsiveness - user keeps on waiting for task to be completed
       * BookKeeper thread -> a runnable, last thread to terminate
       * TODO: maintain a counter = no of threads in pool
         * when thread expires/exit, it can decrement the count
         * when shared var count = 0, read by BookKeeper thread and decremented by Worker threads.
         * when count = 0, bookkeeper would exit too
     
   * One shot and Periodic tasks:
     * Thread Pool execution policy:
       * How tasks are being picked up? - extract FIFO order, extract priority tasks
       * Thread safe priority queue
       * Concrete implementation of interface Blocking Queue is very similar to what we need
     * Write a scheduler
       * Clients should be able to submit tasks & tell when they want tasks to execute
       * One shot task
       * Periodic task - after a task has completed then after defined period
       * Approaches:
         * Fire time = system.current time + gap (at this concrete time, want task to execute)
         * Priority queue -> smallest Fire time number has more priority
         * Execution of tasks ->
           * current system time < current task Fire time
             * while loop continuously check, but cannot sleep in Thread for fix duration i.e. busy waiting
           * one shot task -> easy pick from queue, wait or get job done.
           * periodic task -> after pick 1 second run, then 3, 3, 3... seconds
             * inside while loop, after running task, update Fire time and put into tasksQueue
           
