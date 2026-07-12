package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AmountOfSleepSessions implements Function<List<SleepingSession>, SleepAnalysisResult<Integer>> {
    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sessions) {
        int result = sessions.size();
        return new SleepAnalysisResult<>("Количество сессий сна за представленный период", result);
    }
}
