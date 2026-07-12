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
    private static final int EXPECTED_COLUMNS = 3;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public List<SleepingSession> getListOfSleepingSessions(String fileName) throws IOException {
        try (InputStream inputStream = openStream(fileName);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return br.lines()
                        .filter(line -> !line.isBlank())
                        .map(this::parseLine)
                        .sorted(Comparator.comparing(SleepingSession::getFallingAsleep))
                        .collect(Collectors.toList());
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

    private SleepingSession parseLine(String line) {
        String[] parts = line.split(";");
        if (parts.length < EXPECTED_COLUMNS) {
            throw new IllegalArgumentException(
                    "Недостаточно данных в строке (ожидается " + EXPECTED_COLUMNS
                            + " столбца" + line);
        }

        LocalDateTime fallingAsleep = parseDateTime(parts[0], line);
        LocalDateTime awakening = parseDateTime(parts[1], line);
        if (!awakening.isAfter(fallingAsleep)) {
            throw new IllegalArgumentException(
                    "Пробуждение должно быть позже засыпания: " + line);
        }

        SleepQuality quality = parseQuality(parts[2], line);

        return new SleepingSession(fallingAsleep, awakening, quality);
    }

    private LocalDateTime parseDateTime(String value, String line) {
        try {
            return LocalDateTime.parse(value.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Некорректная дата в строке: " + line, e);
        }
    }

    private SleepQuality parseQuality(String value, String line) {
        try {
            return SleepQuality.valueOf(value.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неизвестное качество сна в строке: " + line, e);
        }
    }
}
