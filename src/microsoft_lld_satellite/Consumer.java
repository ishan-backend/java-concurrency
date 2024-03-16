package microsoft_lld_satellite;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Consumer {
    private int id;
    public void consumeFrame(Frame frame) {
        System.out.println("Frame is consumed by " + getId() + "with frame ts: " + frame.getTimestamp());
    }
}
