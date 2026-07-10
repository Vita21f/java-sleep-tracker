package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeOfUser implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final LocalTime OWL_ASLEEP_AFTER = LocalTime.of(23, 0);
    private static final LocalTime OWL_AWAKE_AFTER = LocalTime.of(9, 0);
    private static final LocalTime LARK_ASLEEP_BEFORE = LocalTime.of(22, 0);
    private static final LocalTime LARK_AWAKE_BEFORE = LocalTime.of(7, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Map<Chronotype, Long> counts = sessions.stream()
                .filter(session -> NightsUtil.coveredNights(session).findAny().isPresent())
                .map(this::classify)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()));

        long owls = counts.getOrDefault(Chronotype.OWL, 0L);
        long larks = counts.getOrDefault(Chronotype.LARK, 0L);
        long doves = counts.getOrDefault(Chronotype.DOVE, 0L);

        Chronotype userType;
        if (owls > larks && owls > doves) {
            userType = Chronotype.OWL;
        } else if (larks > owls && larks > doves) {
            userType = Chronotype.LARK;
        } else {
            userType = Chronotype.DOVE;
        }

        return new SleepAnalysisResult("Хронотип пользователя", userType);
    }

    private Chronotype classify(SleepingSession session) {
        LocalTime start = session.getFallingAsleep().toLocalTime();
        LocalTime end = session.getAwakening().toLocalTime();

        boolean fellAsleepLate = start.isAfter(OWL_ASLEEP_AFTER)
                || start.isBefore(NightsUtil.NIGHT_END);

        if (fellAsleepLate && end.isAfter(OWL_AWAKE_AFTER)) {
            return Chronotype.OWL;
        }
        if (start.isBefore(LARK_ASLEEP_BEFORE)
                && end.isBefore(LARK_AWAKE_BEFORE)) {
            return Chronotype.LARK;
        }
        return Chronotype.DOVE;
    }
}
