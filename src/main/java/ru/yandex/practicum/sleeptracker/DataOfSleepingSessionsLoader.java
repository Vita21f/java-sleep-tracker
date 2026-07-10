package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataOfSleepingSessionsLoader {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public List<SleepingSession> getListOfSleepingSessions(String fileName) throws IOException {
        try (InputStream inputStream = openStream(fileName)) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return br.lines()
                        .filter(line -> !line.isBlank())
                        .map(line -> line.split(";"))
                        .map(this::parseLine)
                        .sorted(Comparator.comparing(SleepingSession::getFallingAsleep))
                        .collect(Collectors.toList());
            }
        }
    }

    private InputStream openStream(String fileName) throws IOException {
        Path path = Path.of(fileName);
        if (Files.exists(path)) {
            return Files.newInputStream(path);
        }
        InputStream fromResources =
                getClass().getResourceAsStream("/" + fileName);
        if (fromResources == null) {
            throw new FileNotFoundException("Файл не найден: " + fileName);
        }
        return fromResources;
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
