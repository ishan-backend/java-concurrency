package microsoft_lld_satellite;

import lombok.Getter;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class FrameProducer implements Runnable {
    private List<Satellite> satellites;
    private ExecutorService executorService;

    public FrameProducer(List<Satellite> satellites) {
        this.satellites = satellites;
        this.executorService = Executors.newFixedThreadPool(2);
    }

    @SneakyThrows
    @Override
    public void run() {
        while(true) {
            Frame frame = generateFrame();
            Thread.sleep(500);
            try {
                for(Satellite satellite: satellites) {
                    executorService.submit(()-> satellite.getFrameQueue().add(frame));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Frame generateFrame() {
        return new Frame(System.currentTimeMillis());
    }
}
