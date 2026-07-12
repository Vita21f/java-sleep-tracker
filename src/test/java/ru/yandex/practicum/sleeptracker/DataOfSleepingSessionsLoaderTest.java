package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataOfSleepingSessionsLoaderTest {
    private final DataOfSleepingSessionsLoader loader = new DataOfSleepingSessionsLoader();

    private Path writeFile(Path tempDir, String... lines) throws IOException {
        Path file = tempDir.resolve("sleep_log.txt");
        Files.write(file, List.of(lines));
        return file;
    }

    @Test
    void loadValidFileReturnsAllSessions(@TempDir Path tempDir) throws IOException {
        Path file = writeFile(tempDir,
                "01.10.25 23:15;02.10.25 07:30;GOOD",
                "02.10.25 23:50;03.10.25 06:40;NORMAL");

        List<SleepingSession> sessions = loader.getListOfSleepingSessions(file.toString());

        assertEquals(2, sessions.size());
    }

    @Test
    void sessionsAreSortedByFallingAsleep(@TempDir Path tempDir) throws IOException {
        Path file = writeFile(tempDir,
                "05.10.25 23:00;06.10.25 07:00;GOOD",      // нарочно в обратном
                "01.10.25 23:15;02.10.25 07:30;NORMAL");   // порядке!

        List<SleepingSession> sessions = loader.getListOfSleepingSessions(file.toString());

        assertTrue(sessions.get(0).getFallingAsleep()
                        .isBefore(sessions.get(1).getFallingAsleep()),
                "Сессии должны быть отсортированы по времени засыпания");
    }

    @Test
    void blankLinesAreSkipped(@TempDir Path tempDir) throws IOException {
        Path file = writeFile(tempDir,
                "01.10.25 23:15;02.10.25 07:30;GOOD",
                "",
                "02.10.25 23:50;03.10.25 06:40;NORMAL");

        List<SleepingSession> sessions = loader.getListOfSleepingSessions(file.toString());

        assertEquals(2, sessions.size());
    }

    @Test
    void missingFileThrowsFileNotFound() {
        assertThrows(FileNotFoundException.class,
                () -> loader.getListOfSleepingSessions("nonexistent.txt"));
    }

    @Test
    void invalidDataThrowsException(@TempDir Path tempDir) throws IOException {
        Path file = writeFile(tempDir,
                "это не дата;тоже не дата;GOOD");

        assertThrows(Exception.class,
                () -> loader.getListOfSleepingSessions(file.toString()));
    }
}
