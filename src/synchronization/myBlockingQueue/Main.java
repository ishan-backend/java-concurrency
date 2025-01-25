package synchronization.myBlockingQueue;

import synchronization.myBlockingQueue.Consumer;
import synchronization.myBlockingQueue.Producer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        MyBlockingQueue queue = new MyBlockingQueue(5);
        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));
        consumer.start();
        producer.start();
    }
}
