package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

public class NightsUtil {
    public static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    public static Stream<LocalDate> coveredNights(SleepingSession session) {
        LocalDateTime start = session.getFallingAsleep();
        LocalDateTime end = session.getAwakening();
        return start.toLocalDate()
                .datesUntil(end.toLocalDate().plusDays(1))
                .filter(day -> start.isBefore(day.atTime(NIGHT_END))
                        && end.isAfter(day.atStartOfDay()));
    }
}
