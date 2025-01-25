package synchronization.myBlockingQueue;

import java.util.LinkedList;
import java.util.Queue;

// MyBlockingQueue for integer
public class MyBlockingQueue {

    // Queue is an interface so internally we are using LL kind of DS
    // add, remove methods in Queue
    private final Queue<Integer> q;
    private final int capacity;

    public MyBlockingQueue(int capacity) {
        this.q = new LinkedList<>();
        this.capacity = capacity;
    }

    public synchronized int take() throws InterruptedException {
        while (q.size() == 0) {
            wait();
        }

        if(q.size() == capacity)
            notifyAll();
        int x = q.poll();
        return x;
    }

    public synchronized void put(Integer i) throws InterruptedException {
        while(q.size() == capacity) {
            wait();
        }

        q.add(i);
        if(q.size() == 1)
            notifyAll();
    }
}
