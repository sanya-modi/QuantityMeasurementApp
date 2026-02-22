package com;

import java.util.Objects;

public class Length {

    private final double value;
    private final LengthUnit unit;

    /**
     * Enum representing supported length units.
     * Base unit: INCHES
     */
    public enum LengthUnit {
        FEET(12.0),
        INCHES(1.0), 
        YARDS(36.0), 
        CENTIMETERS(0.393701);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    /**
     * Constructor
     */
    public Length(double value, LengthUnit unit) {

        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Value must be a valid number");
        }

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = value;
        this.unit = unit;
    }

    /**
     * Convert value to base unit (inches)
     */
    private double convertToBaseUnit() {
        return this.value * this.unit.getConversionFactor();
    }

    /**
     * Compare two Length objects based on base unit
     */
    public boolean compare(Length that) {
        if (that == null) return false;
        return Double.compare(this.convertToBaseUnit(),
                              that.convertToBaseUnit()) == 0;
    }

    /**
     * Value-based equality
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Length that = (Length) o;

        return Double.compare(this.convertToBaseUnit(),
                              that.convertToBaseUnit()) == 0;
    }

    /**
     * Must override hashCode when equals is overridden
     */
    @Override
    public int hashCode() {
        return Objects.hash(convertToBaseUnit());
    }

    @Override
    public String toString() {
        return "Length{" +
                "value=" + value +
                ", unit=" + unit +
                '}';
    }
}