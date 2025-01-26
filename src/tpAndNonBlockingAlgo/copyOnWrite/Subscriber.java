package tpAndNonBlockingAlgo.copyOnWrite;

import java.time.LocalDateTime;
import java.util.Random;

public class Subscriber implements Runnable {
    private final String name;
    private LocalDateTime subscribedAt;
    private final Publisher publisher;

    public Subscriber(String name, Publisher publisher) {
        this.name = name;
        this.publisher = publisher;
    }

    public String GetName() {
        return this.name;
    }

    public void SetSubscribedAt(LocalDateTime subscribedAt) {
        this.subscribedAt = subscribedAt;
    }

    public LocalDateTime GetSubscribedAt() {
        return this.subscribedAt;
    }

    @Override
    public void run() {
        while(true) {
            int i = new Random().nextInt(0, 2);
            if(i == 0) {
                publisher.subscribe(this);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            int j = new Random().nextInt(0, 2);
            if(j == 1) {
                publisher.unSubscribe(this);
            }
        }
    }
}
