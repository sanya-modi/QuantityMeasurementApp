package com.weightmeasurement;

public enum WeightUnit {

    GRAM(1.0),                 // Base
    KILOGRAM(1000.0),          // 1 kg = 1000 g
    POUND(453.59237);          // 1 lb = 453.59237 g (precise)

    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    /**
     * Convert this unit value to grams.
     */
    public double convertToBaseUnit(double value) {
        return value * conversionFactor;
    }

    /**
     * Convert grams to this unit.
     */
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactor;
    }
}