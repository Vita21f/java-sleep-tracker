package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SleepAnalysisResultTest {
    @Test
    void constructor_setsDescriptionAndResultCorrectly() {
        String description = "Тестовый анализ";
        Integer resultValue = 42;

        SleepAnalysisResult result = new SleepAnalysisResult(description, resultValue);

        assertEquals(description, result.getDescription());
        assertEquals(resultValue, result.getResult());
    }

    @Test
    void toString_returnsFormattedString() {
        String description = "Средняя продолжительность сна";
        Integer resultValue = 3600;

        SleepAnalysisResult result = new SleepAnalysisResult(description, resultValue);

        String expected = description + ": " + resultValue;
        assertEquals(expected, result.toString());
    }

    @Test
    void constructor_acceptsDifferentResultTypes() {
        SleepAnalysisResult stringResult = new SleepAnalysisResult("Тип результата", "строка");
        SleepAnalysisResult numberResult = new SleepAnalysisResult("Другой тип", 123);
        SleepAnalysisResult booleanResult = new SleepAnalysisResult("Флаг", true);

        assertNotNull(stringResult);
        assertNotNull(numberResult);
        assertNotNull(booleanResult);
    }
}
