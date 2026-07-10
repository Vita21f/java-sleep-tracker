package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmountSleeplessNightsTest {
    private final AmountSleeplessNights function = new AmountSleeplessNights();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");


    private static SleepingSession session(String from, String to) {
        return new SleepingSession(
                LocalDateTime.parse(from, FORMATTER),
                LocalDateTime.parse(to, FORMATTER),
                SleepQuality.NORMAL);
    }

    private long countSleepless(SleepingSession... sessions) {
        return (long) function.apply(List.of(sessions)).getResult();
    }

    @Test
    void emptyListGivesZero() {
        assertEquals(0L, countSleepless());
    }

    @Test
    void everyNightCoveredBySleepGivesZero() {
        assertEquals(0L, countSleepless(
                session("01.10.25 23:00", "02.10.25 07:00"),
                session("02.10.25 23:30", "03.10.25 06:30")));
    }

    @Test
    void daySleepOnlyCountsAsSleeplessNight() {
        assertEquals(1L, countSleepless(
                session("02.10.25 07:00", "02.10.25 11:00")));
    }

    @Test
    void sleepStartedAfterMidnightCoversTheNight() {
        assertEquals(0L, countSleepless(
                session("05.10.25 00:10", "05.10.25 06:20")));
    }

    @Test
    void singleAfternoonSessionGivesZeroNightsInPeriod() {
        assertEquals(0L, countSleepless(
                session("01.10.25 14:00", "01.10.25 15:00")));
    }

    @Test
    void gapBetweenNightSessionsGivesOneSleeplessNight() {
        assertEquals(1L, countSleepless(
                session("01.10.25 23:00", "02.10.25 07:00"),
                session("03.10.25 23:00", "04.10.25 07:00")));
    }

    @Test
    void periodCrossingMonthBoundaryIsCountedCorrectly() {
        assertEquals(32L, countSleepless(
                session("25.10.25 23:00", "26.10.25 07:00"),
                session("27.11.25 23:00", "28.11.25 07:00")));
    }
}
