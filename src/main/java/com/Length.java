package com;

import java.util.Objects;

public final class Length {

    private final double value;
    private final LengthUnit unit;

    private static final double EPSILON = 1e-6;

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

    /**
     * Convert this length to target unit
     */
    public Length convertTo(LengthUnit targetUnit) {

        double baseValue = unit.convertToBaseUnit(value);

        double convertedValue =
                targetUnit.convertFromBaseUnit(baseValue);

        return new Length(convertedValue, targetUnit);
    }

    /**
     * UC6 addition (result in first operand unit)
     */
    public Length add(Length that) {
        return add(that, this.unit);
    }

    /**
     * UC7 addition (explicit target unit)
     */
    public Length add(Length that, LengthUnit targetUnit) {

        if (that == null)
            throw new IllegalArgumentException("Length cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double thisBase =
                this.unit.convertToBaseUnit(this.value);

        double thatBase =
                that.unit.convertToBaseUnit(that.value);

        double sumBase = thisBase + thatBase;

        double result =
                targetUnit.convertFromBaseUnit(sumBase);

        return new Length(result, targetUnit);
    }

    private boolean compare(Length that) {

        double thisBase =
                this.unit.convertToBaseUnit(this.value);

        double thatBase =
                that.unit.convertToBaseUnit(that.value);

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

        double normalized =
                unit.convertToBaseUnit(value);

        return Objects.hash(
                Math.round(normalized / EPSILON)
        );
    }

    @Override
    public String toString() {
        return String.format("Quantity(%.6f, %s)",
                value, unit);
    }
}