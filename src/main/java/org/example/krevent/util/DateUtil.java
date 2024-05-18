package org.example.krevent.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class DateUtil {
    private static final ZoneId ZONE_ID = ZoneId.of("Europe/Sofia");
    private static final List<String> DATE_FORMATS = Arrays.asList(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", // ISO Instant
            "yyyy-MM-dd'T'HH:mm:ss.SSS",    // ISO Local Date Time
            "yyyy-MM-dd'T'HH:mm:ss",        // ISO Local Date Time without milliseconds
            "yyyy-MM-dd HH:mm:ss",          // Custom format
            "yyyy-MM-dd",                   // ISO Local Date
            "dd MMM uuuu"                   // Custom format
    );

    public static LocalDateTime parse(String dateString) {
        for (String format : DATE_FORMATS) {
            try {
                return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(format));
            } catch (DateTimeParseException ignored) {
            }
        }
        // none of the formats worked
        throw new IllegalArgumentException("Invalid date format: " + dateString);
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZONE_ID);
    }
}
