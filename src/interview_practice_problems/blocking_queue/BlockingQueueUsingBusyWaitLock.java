package interview_practice_problems.blocking_queue;

// Let's see how the implementation would look like, if we were restricted to using a mutex.
// There's no direct equivalent of a theoretical mutex in Java as each object has an implicit monitor associated with it.

// we'll use an object of the Lock class and pretend it doesn't expose the wait() and notify() methods and only provides mutual exclusion similar to a theoretical mutex
// Without the ability to wait or signal the implication is, a blocked thread will constantly poll in a loop for a predicate/condition to become true before making progress.
// This is an example of a busy-wait solution.

public class BlockingQueueUsingBusyWaitLock {

}
