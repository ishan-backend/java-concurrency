package interview_practice_problems.blocking_queue;

public class BlockingQueueUsingSynchronized<T>{
    T[] array;
    int capacity;
    int size = 0;
    int head = 0;
    int tail = 0;

    @SuppressWarnings("unchecked")
    public BlockingQueueUsingSynchronized(int capacity) {
        this.capacity = capacity;
        array = (T[]) new Object[capacity]; // The casting results in a warning
    }

    /*
    synchronized === monitor -> takes lock on this
    // in java each object has monitor associated to it

    * you could have also done like

    Object lock = new Object();
    public void enqueue(T item) throws InterruptedException {

        synchronized (lock) {

            while (size == capacity) {
                lock.wait();
            }

            if (tail == capacity) {
                tail = 0;
            }

            array[tail] = item;
            size++;
            tail++;
            lock.notifyAll();
        }
    }
    */
    public synchronized void enqueue(T item) throws InterruptedException { //
        // wait for queue to have space
        while (size == capacity) {
            wait();
        }

        // reset tail to the beginning if the tail is already
        // at the end of the backing array
        if (tail == capacity) {
            tail = 0;
        }

        // place the item in the array
        array[tail] = item;
        size++;
        tail++;

        // don't forget to notify any other threads waiting on a change in value of size. There might be consumers waiting for the queue to have atleast one element
        // If no thread is waiting, then the signal will simply go unnoticed and be ignored, which wouldn’t affect the correct working of our class.
        // This would be an instance of missed signal that we have talked about earlier.
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {

        T item = null;

        // we need to block the caller of the dequeue method if there’s nothing to dequeue
        while (size == 0) {
            wait();
        }

        // reset head to start of array if its past the array
        if (head == capacity) {
            head = 0;
        }

        // store the reference to the object being dequeued
        // and overwrite with null
        item = array[head];
        array[head] = null;
        head++;
        size--;

        // don't forget to call notify, if the queue were full then there might be producer threads blocked in the enqueue method.
        notifyAll();

        return item;
    }
}
