package com.measurement;

/**
 * Common contract for all measurable unit types.
 * Provides base-unit conversion behavior.
 */
public interface IMeasurable {

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    String getUnitName();
}