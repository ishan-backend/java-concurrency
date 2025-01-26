package tpAndNonBlockingAlgo.copyOnWrite;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private final List<Subscriber> subscribers;
    public Publisher() {
        this.subscribers = new ArrayList<>();
    }

    public synchronized void subscribe(Subscriber s) { // synchronized as multiple threads would tamper with subscribers
        if(this.subscribers.contains(s))
            return;

        s.SetSubscribedAt(LocalDateTime.now());
        this.subscribers.add(s);
    }

    public synchronized void unSubscribe(Subscriber s) {
        if(!this.subscribers.contains(s))
            return;
        this.subscribers.remove(s);
    }
}
