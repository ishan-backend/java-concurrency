package microsoft_lld_satellite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Satellite implements Runnable{
    private FixedSizeQueue<Frame> frameQueue;
    private List<Consumer> consumerList;
    private ExecutorService executorService;

    public Satellite(int maxSize) {
        this.frameQueue = new FixedSizeQueue<Frame>(maxSize);
        this.consumerList = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void addConsumer(Consumer consumer) {
        consumerList.add(consumer);
    }

    @Override
    public void run() {
        while(true) {
            Frame frame = frameQueue.poll();
            if(frame != null) {
                for(Consumer consumer: consumerList) {
                    executorService.submit(()->
                        consumer.consumeFrame(frame)
                    );
                }
            }
        }
    }

    public FixedSizeQueue<Frame> getFrameQueue() {
        return this.frameQueue;
    }
}
