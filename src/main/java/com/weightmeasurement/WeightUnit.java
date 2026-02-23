package com.weightmeasurement;

import com.measurement.IMeasurable;

public enum WeightUnit implements IMeasurable {

    GRAM(1.0),            // Base unit
    KILOGRAM(1000.0),
    MILLIGRAM(0.001),
    POUND(453.59237),
    TONNE(1_000_000.0);

    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value * conversionFactor;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactor;
    }

    @Override
    public String getUnitName() {
        return name();
    }
}