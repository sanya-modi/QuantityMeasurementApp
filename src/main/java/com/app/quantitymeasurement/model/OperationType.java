package com.app.quantitymeasurement.model;

import java.util.Locale;

public enum OperationType {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    COMPARE,
    CONVERT;

    public static OperationType fromValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Operation type is required");
        }

        try {
            return OperationType.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid operation type: " + value, exception);
        }
    }
}
