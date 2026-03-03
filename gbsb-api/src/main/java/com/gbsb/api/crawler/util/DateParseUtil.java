package com.gbsb.api.crawler.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 크롤러에서 공통으로 사용하는 날짜 파싱 유틸리티.
 * 다양한 날짜 형식을 시도하여 LocalDateTime으로 변환한다.
 */
public final class DateParseUtil {

    private static final String[] DATETIME_PATTERNS = {
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd'T'HH:mm:ss",
        "yyyy-MM-dd'T'HH:mm:ssXXX",
        "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
    };

    private static final String[] DATE_PATTERNS = {
        "yyyy-MM-dd",
        "yyyy.MM.dd",
        "yyyy/MM/dd",
        "MM/dd/yyyy",
        "yyyy년 MM월 dd일",
    };

    private DateParseUtil() {
        // utility class
    }

    /**
     * 다양한 형식의 날짜 문자열을 LocalDateTime으로 파싱한다.
     * 파싱 실패 시 현재 시각을 반환한다.
     */
    public static LocalDateTime parse(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return LocalDateTime.now();
        }

        String trimmed = dateStr.trim();

        // ISO 8601 with timezone offset → strip to first 19 chars
        if (trimmed.contains("T")) {
            try {
                return LocalDateTime.parse(trimmed.substring(0, Math.min(19, trimmed.length())));
            } catch (DateTimeParseException ignored) {
                // try other patterns
            }
        }

        // Try DateTime patterns
        for (String pattern : DATETIME_PATTERNS) {
            try {
                return LocalDateTime.parse(trimmed, DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException ignored) {
                // try next
            }
        }

        // Try Date-only patterns (will set time to 00:00:00)
        for (String pattern : DATE_PATTERNS) {
            try {
                LocalDate date = LocalDate.parse(trimmed, DateTimeFormatter.ofPattern(pattern));
                return date.atStartOfDay();
            } catch (DateTimeParseException ignored) {
                // try next
            }
        }

        return LocalDateTime.now();
    }
}
