package com;

import java.util.Objects;

public class Length {

    private final double value;
    private final LengthUnit unit;

    // Base Unit = INCHES
    public enum LengthUnit {
        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

        private final double conversionFactor; // relative to inches

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    //CONSTRUCTOR 
    public Length(double value, LengthUnit unit) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = value;
        this.unit = unit;
    }

    // PRIVATE BASE CONVERSION 
    private double convertToBaseUnit() {
        return this.value * this.unit.getConversionFactor();
    }

    // Instance conversion method
    public Length convertTo(LengthUnit targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        // Step 1: Convert to base (inches)
        double baseValue = convertToBaseUnit();

        // Step 2: Convert base → target
        double convertedValue = baseValue / targetUnit.getConversionFactor();

        // Optional rounding (2 decimal places)
        convertedValue = Math.round(convertedValue * 100.0) / 100.0;

        return new Length(convertedValue, targetUnit);
    }

    //EQUALITY 
    private boolean compare(Length that) {

        double thisBase =
                Math.round(this.convertToBaseUnit() * 100.0) / 100.0;

        double thatBase =
                Math.round(that.convertToBaseUnit() * 100.0) / 100.0;

        return Double.compare(thisBase, thatBase) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Length)) return false;

        Length that = (Length) o;
        return compare(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(convertToBaseUnit());
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }
}