package producer_consumer;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue {
    private Queue<Integer> queue; // Integer is wrapper (non-primitive around int i.e. primitive type)
    private int capacity;

    public BlockingQueue(int cap) {
        this.queue = new LinkedList<>();
        this.capacity = cap;
    }

    public boolean add(int item) {
        synchronized (queue) { // only one thread will enter this below critical section at a time over queue lock
            while(queue.size() == capacity) {
                try {
                    queue.wait(); // t1 may enter wait state i.e. it releases lock over queue. So t2, might also acquire lock over queue and enter wait
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            queue.add(item);
            queue.notifyAll(); // notifies all other threads, they are then blocked on lock acquisition
            return true;
        }
    }

    public int remove() {
        synchronized (queue) {
            while(queue.isEmpty()) {
                try {
                    queue.wait(); // thread that has lock on queue object will have to wait() - it internally maintains wait set
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int element = queue.poll();
            queue.notifyAll();
            return element;
        }
    }
}
