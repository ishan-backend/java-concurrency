package microsoft_lld_satellite;

import java.util.ArrayDeque;

public class FixedSizeQueue<T> {
    private ArrayDeque<T> queue;
    private int maxSize;

    public FixedSizeQueue (final int size) {
        this.queue = new ArrayDeque<>();
        this.maxSize = size;
    }

    public synchronized void add(T element) {
        if(queue.size() >= maxSize) {
            queue.removeFirst();
        }
        queue.add(element);
    }

    public synchronized T poll() {
        return queue.pollFirst();
    }

    public synchronized int size() {
        return queue.size();
    }
}
