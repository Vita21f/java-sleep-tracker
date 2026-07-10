package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataOfSleepingSessionsLoader {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public List<SleepingSession> getListOfSleepingSessions(String fileName) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/" + fileName)) {
            if (inputStream == null) {
                System.err.println("Файл не найден в ресурсах: " + fileName);
                throw new FileNotFoundException("Файл не найден в ресурсах: " + fileName);
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                List<SleepingSession> sessions = br.lines()
                        .filter(line -> !line.isBlank())
                        .map(line -> line.split(";"))
                        .map(this::parseLine)
                        .sorted(Comparator.comparing(sleepingSession -> sleepingSession.getFallingAsleep()))
                        .collect(Collectors.toList());
                return sessions;
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла " + fileName + ": " + e.getMessage());
                return List.of();
            }
        }
    }

    private SleepingSession parseLine(String[] line) {
        try {
            LocalDateTime dt1 = LocalDateTime.parse(line[0], formatter);
            LocalDateTime dt2 = LocalDateTime.parse(line[1], formatter);
            SleepQuality enumValue = SleepQuality.valueOf(line[2].toUpperCase().trim());
            return new SleepingSession(dt1, dt2, enumValue);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            System.err.println("Некорректные данные в строке: " + String.join(";", line));
            throw e;
        }
        }
}
