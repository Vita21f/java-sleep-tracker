package ru.yandex.practicum.sleeptracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {
    private final List<Function<List<SleepingSession>, ? extends SleepAnalysisResult<?>>> functions;

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
                SleepAnalysisResult<?> result = function.apply(sessions);
                System.out.println(result);
            });
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка в данных файла: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Укажите путь к файлу с логом сна: java SleepTrackerApp <путь>");
            return;
        }
        SleepTrackerApp app = new SleepTrackerApp();
        app.runAnalysis(args[0]);
    }
}

