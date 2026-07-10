package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MinSessionDuration implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Optional<Duration> minDurationOpt = sessions.stream()
                .map(session -> Duration.between(session.getFallingAsleep(), session.getAwakening()))
                .min(Duration::compareTo);

        Duration minDuration = minDurationOpt.orElse(Duration.ofSeconds(0));

        return new SleepAnalysisResult("Минимальная продолжительность сессии (в минутах)", minDuration.toMinutes());
    }
}
