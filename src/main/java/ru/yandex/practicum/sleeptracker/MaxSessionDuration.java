package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MaxSessionDuration implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Optional<Duration> maxDurationOpt = sessions.stream()
                .map(session -> Duration.between(session.getFallingAsleep(), session.getAwakening()))
                .max(Duration::compareTo);

        Duration maxDuration = maxDurationOpt.orElse(Duration.ofSeconds(0));

        return new SleepAnalysisResult("Максимальная продолжительность сессии (в минутах)", maxDuration.toMinutes());
    }
}
