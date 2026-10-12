package com.calc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 계산 히스토리 항목을 나타내는 Java 21 불변 record
 */
public record HistoryItem(
        String expression,
        String formattedResult,
        BigDecimal rawValue,
        LocalDateTime timestamp
) {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public HistoryItem(String expression, String formattedResult, BigDecimal rawValue) {
        this(expression, formattedResult, rawValue, LocalDateTime.now());
    }

    public String formattedTime() {
        return timestamp.format(TIME_FORMATTER);
    }
}
