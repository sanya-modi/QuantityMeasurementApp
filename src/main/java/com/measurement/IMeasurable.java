package com.measurement;

/**
 * Common contract for all measurable unit types.
 * Provides base-unit conversion behavior.
 */
public interface IMeasurable {

    // Default lambda – all units support arithmetic by default
    SupportsArithmetic supportsArithmetic = () -> true;

    // Mandatory conversion methods
    String getUnitName();
    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);

    // Optional arithmetic capability check
    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    // Optional validation hook
    default void validateOperationSupport(String operation) {
        // default: do nothing (all units support arithmetic)
    }
}