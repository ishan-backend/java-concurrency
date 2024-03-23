package interview_practice_problems.blocking_queue;

// The enqueue-er thread initially fills up the queue and gets blocked, till the dequeuer threads start off and remove elements from the queue.
// The output would show enqueuing and dequeuing activity interleaved after the first 5 enqueues.
@SuppressWarnings("ALL") // new Integer(i) is deprecated
public class DemonstrationUsingSynchronized {
    public static void main(String[] args) throws InterruptedException {
        final BlockingQueueUsingSynchronized<Integer> q = new BlockingQueueUsingSynchronized<Integer>(5);

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    for(int i=0; i<50; i++) {
                        q.enqueue(new Integer(i));
                        System.out.println("Thread 1 enqueued: "  + i );
                    }
                } catch (InterruptedException ie) { // required to prevent using @SneakyThrows

                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=0; i<25; i++) {
                        System.out.println("Thread 2 dequed: " + q.dequeue());
                    }
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=0; i<25; i++) {
                        System.out.println("Thread 3 dequed: " + q.dequeue());
                    }
                } catch (InterruptedException ie) {

                }
            }
        });

        t1.start();
        Thread.sleep(500);
        t2.start();
        t2.join();

        t3.start();
        t1.join();
        t3.join();
    }
}
