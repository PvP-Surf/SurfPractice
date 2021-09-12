package surf.pvp.practice.util.cooldown;

import lombok.Data;

@Data
public class SimpleCooldown {

    private final long duration;

    private long start;
    private long expire;
    private boolean notified;

    public SimpleCooldown(long duration) {
        this.duration = duration;

        if (duration == 0) {
            this.notified = true;
        }
    }

    public void put() {
        this.start = System.currentTimeMillis();
        this.expire = this.start + duration;
    }

    public long getPassed() {
        return System.currentTimeMillis() - this.start;
    }

    public long getRemaining() {
        return this.expire - System.currentTimeMillis();
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() - this.expire >= 0;
    }

    public String getTimeLeft() {
        return DurationFormatter.getRemaining(this.getRemaining(), true);
    }

}
