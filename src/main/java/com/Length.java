package com;

import java.util.Objects;

public final class Length {

    private final double value;
    private final LengthUnit unit;

    private static final double EPSILON = 1e-6;

    public enum LengthUnit {
        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(1.0 / 2.54);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    public Length(double value, LengthUnit unit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    double convertToBaseUnit() {
        return value * unit.getConversionFactor();
    }

    public Length convertTo(LengthUnit targetUnit) {
        double base = convertToBaseUnit();
        double converted = base / targetUnit.getConversionFactor();
        return new Length(converted, targetUnit);
    }

    private boolean compare(Length that) {
        double thisBase = this.convertToBaseUnit();
        double thatBase = that.convertToBaseUnit();

        return Math.abs(thisBase - thatBase) <= EPSILON;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Length)) return false;
        return compare((Length) o);
    }

    @Override
    public int hashCode() {
        long normalized = Math.round(convertToBaseUnit() / EPSILON);
        return Long.hashCode(normalized);
    }

    @Override
    public String toString() {
        return String.format("%.6f %s", value, unit);
    }
}