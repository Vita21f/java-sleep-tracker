package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxSessionDurationTest {
    MaxSessionDuration analyzer = new MaxSessionDuration();

    @Test
    void apply_withThreeSessions_returnMaxDuration() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 15),
                        LocalDateTime.of(2025, 10, 2, 7, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 22, 15),
                        LocalDateTime.of(2025, 10, 3, 9, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 21, 15),
                        LocalDateTime.of(2025, 10, 4, 7, 30),
                        SleepQuality.GOOD
                )
        );
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(Long.valueOf(675), result.getResult());
        assertEquals("Максимальная продолжительность сессии (в минутах)", result.getDescription());
    }

    @Test
    void apply_withEmptyList_returnZero() {
        List<SleepingSession> emptySessions = List.of();
        SleepAnalysisResult result = analyzer.apply(emptySessions);

        assertEquals(Long.valueOf(0), result.getResult());
        assertEquals("Максимальная продолжительность сессии (в минутах)", result.getDescription());
    }
}
