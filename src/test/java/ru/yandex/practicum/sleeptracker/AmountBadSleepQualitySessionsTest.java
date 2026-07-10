package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmountBadSleepQualitySessionsTest {
    AmountBadSleepQualitySessions analyzer = new AmountBadSleepQualitySessions();

    @Test
    void apply_withThreeSessions_returnAmountBadSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 15),
                        LocalDateTime.of(2025, 10, 2, 7, 30),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 22, 15),
                        LocalDateTime.of(2025, 10, 3, 9, 30),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 21, 15),
                        LocalDateTime.of(2025, 10, 4, 7, 30),
                        SleepQuality.GOOD
                )
        );
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(Long.valueOf(2), result.getResult());
        assertEquals("Количество сессий с плохим качеством сна", result.getDescription());
    }

    @Test
    void apply_withEmptyList_returnZero() {
        List<SleepingSession> emptySessions = List.of();
        SleepAnalysisResult result = analyzer.apply(emptySessions);

        assertEquals(Long.valueOf(0), result.getResult());
        assertEquals("Количество сессий с плохим качеством сна", result.getDescription());
    }
}
