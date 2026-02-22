package com.weightmeasurement;

import java.util.Objects;

public final class QuantityWeight {

    private static final double EPSILON = 1e-3;

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    /**
     * Normalize to grams.
     */
    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    /**
     * Convert to another unit.
     */
    public QuantityWeight convertTo(WeightUnit targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue = toBaseUnit();
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        return new QuantityWeight(convertedValue, targetUnit);
    }

    /**
     * Add another weight (result in current unit).
     */
    public QuantityWeight add(QuantityWeight other) {

        Objects.requireNonNull(other, "Other weight cannot be null");

        double sumBase = this.toBaseUnit() + other.toBaseUnit();
        double resultValue = this.unit.convertFromBaseUnit(sumBase);

        return new QuantityWeight(resultValue, this.unit);
    }

    /**
     * Add another weight with explicit target unit.
     */
    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {

        Objects.requireNonNull(other, "Other weight cannot be null");

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double sumBase = this.toBaseUnit() + other.toBaseUnit();
        double resultValue = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityWeight(resultValue, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    /**
     * hashCode consistent with equals.
     * We round base value to avoid floating mismatch.
     */
    @Override
    public int hashCode() {
        long rounded = Math.round(toBaseUnit() * 1000);
        return Long.hashCode(rounded);
    }

    @Override
    public String toString() {
        return String.format("QuantityWeight[%.6f %s]", value, unit);
    }
}
