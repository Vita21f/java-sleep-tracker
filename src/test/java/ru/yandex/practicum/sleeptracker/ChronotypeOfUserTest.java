package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.sleeptracker.Chronotype.*;

public class ChronotypeOfUserTest {
    private final ChronotypeOfUser function = new ChronotypeOfUser();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private static SleepingSession session(String from, String to) {
        return new SleepingSession(
                LocalDateTime.parse(from, FORMATTER),
                LocalDateTime.parse(to, FORMATTER),
                SleepQuality.NORMAL);
    }

    private Object getChronotype(SleepingSession... sessions) {
        return function.apply(List.of(sessions)).getResult();
    }

    @Test
    void emptyListGivesDove() {
        assertEquals(DOVE, getChronotype());
    }

    @Test
    void equalNumberOfOwlsAndLarksGivesDove() {
        assertEquals(DOVE, getChronotype(
                session("01.10.25 23:01", "02.10.25 11:00"),
                session("02.10.25 23:30", "03.10.25 12:30"),
                session("03.10.25 21:01", "04.10.25 06:00"),
                session("04.10.25 20:30", "05.10.25 06:30")));
    }

    @Test
    void fallingAsleepExactlyAt23GivesDove() {
        assertEquals(DOVE, getChronotype(
                session("01.10.25 23:00", "02.10.25 11:00")));
    }

    @Test
    void onlyOwlSessionsGiveOwl() {
        assertEquals(OWL, getChronotype(
                session("01.10.25 23:01", "02.10.25 11:00"),
                session("02.10.25 23:30", "03.10.25 12:30")));
    }

    @Test
    void onlyLarkSessionsGiveLark() {
        assertEquals(LARK, getChronotype(
                session("03.10.25 21:01", "04.10.25 06:00"),
                session("04.10.25 20:30", "05.10.25 06:30")));
    }

    @Test
    void onlyDayTimeSleepGivesDove() {
        assertEquals(DOVE, getChronotype(
                session("03.10.25 13:00", "03.10.25 14:00")));
    }

    @Test
    void fallingAsleepAfterMidnightGivesOwl() {
        assertEquals(OWL, getChronotype(
                session("04.10.25 00:50", "04.10.25 14:00")));
    }

    @Test
    void multiNightSessionVotesForEachNight() {
        assertEquals(OWL, getChronotype(
                session("01.10.25 23:30", "03.10.25 09:30"),
                session("04.10.25 21:00", "05.10.25 06:30")
        ));
    }
}
