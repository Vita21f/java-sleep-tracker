package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DataOfSleepingSessionsLoaderTest {
    private final DataOfSleepingSessionsLoader loader = new DataOfSleepingSessionsLoader();
    private static final String TEST_FILE = "sleep_log.txt";

    @Test
    void testLoadValidFile() throws IOException {
        List<SleepingSession> sessions = loader.getListOfSleepingSessions(TEST_FILE);

        assertNotNull(sessions);
        assertFalse(sessions.isEmpty());
        assertEquals(13, sessions.size());
    }
    @Test
    void testSortingInCorrect() throws IOException {
        List<SleepingSession> sessions = loader.getListOfSleepingSessions(TEST_FILE);

        for (int i = 0; i < sessions.size() - 1; i++) {
            assertTrue(
                    sessions.get(i).getFallingAsleep()
                            .isBefore((sessions.get(i + 1).getFallingAsleep())
                            ), "Сессии должны быть отсортированы по времени засыпания"
            );
        }
    }
    @Test
    void testFileNotFound() {
        String nonexistentFile = "nonexistent.txt";

        assertThrows(FileNotFoundException.class, () -> {
            loader.getListOfSleepingSessions(nonexistentFile);
        });
    }


}
