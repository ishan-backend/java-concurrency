package stack_lld;

import lombok.Getter;

@Getter
public class Stack {
    private int[] array;
    private int stackTop;
    Object lock; // synchronized or lock has to be applied on some Object apart from primitive types
    // no of locks = no of doors = no of threads that can access different methods parallel

    public Stack(int cap) {
        array = new int[cap];
        stackTop = -1;
        lock = new Object();
    }

    // t1, t2, t3
    public boolean push(int element) {
        synchronized (lock) {
            // below is critical section of code
            // synchronized allows only one thread to enter and exit it at a time
            if(isFull()) return false;
            ++stackTop; // if synchronized is not used and scheduler evicts after this line, it may cause inconsistency in memory index access

            try { Thread.sleep(1000); } catch (InterruptedException e){ };
            array[stackTop] = element;
            return true;
        }
    }
    /*
    * Or, above method can also be written like:
    *
    public synchronized boolean push(int element) {
            // below is critical section of code
            // synchronized allows only one thread to enter and exit it at a time
            if(isFull()) return false;
            ++stackTop; // if synchronized is not used and scheduler evicts after this line, it may cause inconsistency in memory index access

            try { Thread.sleep(1000); } catch (InterruptedException e){ };
            array[stackTop] = element;
            return true;
    }
    *
    * Behind the scenes it means that, it is something like:
    *
    public boolean push(int element) {
        synchronized(this) { // uses instance of current object of Stack as lock
            if(isFull()) return false;
            ++stackTop;
            try { Thread.sleep(1000); } catch (InterruptedException e){ };
            array[stackTop] = element;
            return true;
       }
    }
    *
    * If you have multiple such synchronized methods -> only one thread will have access to all such synchronized methods
    * */

    // t1, t4, t5
    public int pop() {
        synchronized (lock) {
            if(isEmpty()) return Integer.MIN_VALUE;
            int obj = array[stackTop];
            array[stackTop] = Integer.MIN_VALUE;

            try { Thread.sleep(1000); } catch (InterruptedException e){ };
            stackTop--;
            return obj;
        }
    }

    /*
    * If t2 acquired push(), then t1 cannot go ahead and execute pop() since C.S is bounded by same lock object
    * In other words,
    * only t2 would be allowed to access both push() and pop() at same time since it is bounded by same lock object
    *
    * If t2 is pushing, other threads wont be able to push() or pop() and vice-versa
    * */

    public boolean isEmpty() {
        if(stackTop == -1) return true;
        return false;
    }

    public boolean isFull() {
        if(stackTop == array.length-1) return true;
        return false;
    }
}
