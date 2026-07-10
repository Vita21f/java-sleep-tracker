package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {
    private final List<Function<List<SleepingSession>, SleepAnalysisResult>> functions;

    public SleepTrackerApp() {
        functions = new ArrayList<>();
        addFunctions();
    }

    private void addFunctions() {
        functions.add(new AmountOfSleepSessions());
        functions.add(new MinSessionDuration());
        functions.add(new MaxSessionDuration());
        functions.add(new AverageSessionDuration());
        functions.add(new AmountBadSleepQualitySessions());
        functions.add(new AmountSleeplessNights());
        functions.add(new ChronotypeOfUser());
    }

    public void runAnalysis(String fileName) {
        DataOfSleepingSessionsLoader loader = new DataOfSleepingSessionsLoader();
        try {
            List<SleepingSession> sessions = loader.getListOfSleepingSessions(fileName);

            System.out.println("=== АНАЛИЗ ДАННЫХ О СНЕ ===");

            functions.forEach(function -> {
                SleepAnalysisResult result = function.apply(sessions);
                System.out.println(result);
            });
        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SleepTrackerApp app = new SleepTrackerApp();
        app.runAnalysis("sleep_log.txt");
    }
}

