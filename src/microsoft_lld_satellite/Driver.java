package microsoft_lld_satellite;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        // create satellite with 120 FPS
        Satellite satellite = new Satellite(120);
        // create and add consumers to satellite
        Consumer c1 = new Consumer(1);
        Consumer c2 = new Consumer(2);
        Consumer c3 = new Consumer(3);
        satellite.addConsumer(c1);
        satellite.addConsumer(c2);
        satellite.addConsumer(c3);

        // start satellite in separate thread
        Thread satelliteThread = new Thread(satellite);
        satelliteThread.start();

        // add frames at 120 FPS
        List<Satellite> satellites = new ArrayList<>();
        satellites.add(satellite);
        FrameProducer frameProducer = new FrameProducer(satellites);
        Thread frameProducerThread = new Thread(frameProducer);
        frameProducerThread.start();

        
    }
}
