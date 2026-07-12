package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime fallingAsleep;
    private final LocalDateTime awakening;
    private final SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime fallingAsleep, LocalDateTime awakening, SleepQuality sleepQuality) {
        this.fallingAsleep = fallingAsleep;
        this.awakening = awakening;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getFallingAsleep() {
        return fallingAsleep;
    }

    public LocalDateTime getAwakening() {
        return awakening;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }
}
