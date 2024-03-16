package microsoft_lld_satellite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Frame {
    private long timestamp;
    // other frame metadata
    public Frame(long timestamp) {
        this.timestamp = timestamp;
    }
}
