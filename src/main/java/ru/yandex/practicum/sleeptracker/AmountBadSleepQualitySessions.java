package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AmountBadSleepQualitySessions implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long badSleepQualitySessions = sessions.stream()
                .filter(session -> SleepQuality.BAD.equals(session.getSleepQuality()))
                .count();

        return new SleepAnalysisResult("Количество сессий с плохим качеством сна", badSleepQualitySessions);
    }
}
