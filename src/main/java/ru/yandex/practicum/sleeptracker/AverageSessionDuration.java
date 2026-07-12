package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageSessionDuration implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Средняя продолжительность сессии (в минутах)", 0L);
        }

        Duration totalDuration = sessions.stream()
                .map(session -> Duration.between(session.getFallingAsleep(), session.getAwakening()))
                .reduce(Duration.ZERO, Duration::plus);

        long averageMinutes = totalDuration.dividedBy(sessions.size()).toMinutes();

        return new SleepAnalysisResult<>("Средняя продолжительность сессии (в минутах)", averageMinutes);
    }
}
