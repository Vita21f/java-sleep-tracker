package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class AmountSleeplessNights implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String DESCRIPTION = "Количество бессонных ночей";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION, 0L);
        }

        LocalDateTime loggingStart = sessions.stream()
                .map(SleepingSession::getFallingAsleep)
                .min(Comparator.naturalOrder())
                .orElseThrow();

        LocalDateTime loggingEnd = sessions.stream()
                .map(SleepingSession::getAwakening)
                .max(Comparator.naturalOrder())
                .orElseThrow();

        LocalDate firstNight = loggingStart.toLocalTime().isBefore(LocalTime.NOON)
                ? loggingStart.toLocalDate()
                : loggingStart.toLocalDate().plusDays(1);

        LocalDate lastNight = loggingEnd.toLocalDate();

        long totalNights = ChronoUnit.DAYS.between(firstNight, lastNight.plusDays(1));

        if (totalNights <= 0) {
            return new SleepAnalysisResult(DESCRIPTION, 0L);
        }

        long sleptNights = sessions.stream()
                .flatMap(NightsUtil::coveredNights)
                .distinct()
                .count();
        return new SleepAnalysisResult(DESCRIPTION, totalNights - sleptNights);
    }
}
