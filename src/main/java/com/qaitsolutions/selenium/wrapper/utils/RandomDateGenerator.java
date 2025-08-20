package com.qaitsolutions.selenium.wrapper.utils;

import java.time.LocalDate;
import java.util.Random;

public class RandomDateGenerator {

    private static final Random random = new Random();

    /**
     * Generates a random LocalDate between the given start and end dates.
     *
     * @param startInclusive The start date (inclusive).
     * @param endInclusive   The end date (inclusive).
     * @return A random LocalDate.
     */
    public static LocalDate generateRandomDate(LocalDate startInclusive, LocalDate endInclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endInclusive.toEpochDay();
        long randomEpochDay = startEpochDay + random.nextInt((int) (endEpochDay - startEpochDay + 1));
        return LocalDate.ofEpochDay(randomEpochDay);
    }
}
