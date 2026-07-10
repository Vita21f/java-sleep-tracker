package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmountOfSleepSessionsTest {
    AmountOfSleepSessions analyzer = new AmountOfSleepSessions();

    @Test
    void apply_withThreeSessions_returnCount() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 15),
                        LocalDateTime.of(2025, 10, 2, 7, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 15),
                        LocalDateTime.of(2025, 10, 3, 7, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 15),
                        LocalDateTime.of(2025, 10, 4, 7, 30),
                        SleepQuality.GOOD
                )
        );
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(3, result.getResult());
        assertEquals("Количество сессий сна за представленный период", result.getDescription());
    }

    @Test
    void apply_withEmptyList_returnZero() {
        List<SleepingSession> emptySessions = List.of();
        SleepAnalysisResult result = analyzer.apply(emptySessions);

        assertEquals(0, result.getResult());
        assertEquals("Количество сессий сна за представленный период", result.getDescription());
    }
}

