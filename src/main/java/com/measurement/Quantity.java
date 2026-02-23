package com.measurement;

import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    // Conversion
    public Quantity<U> convertTo(U targetUnit) {

        if (!unit.getClass().equals(targetUnit.getClass()))
            throw new IllegalArgumentException("Incompatible unit category");

        double baseValue = unit.convertToBaseUnit(value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(round(convertedValue), targetUnit);
    }

    // Addition
    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        if (!unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Cannot add different categories");

        double base1 = unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sumBase = base1 + base2;
        double finalValue = targetUnit.convertFromBaseUnit(sumBase);

        return new Quantity<>(round(finalValue), targetUnit);
    }

    // Equality
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (!(obj instanceof Quantity<?>))
            return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (!unit.getClass().equals(other.unit.getClass()))
            return false;

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Double.compare(round(base1), round(base2)) == 0;
    }

    @Override
    public int hashCode() {
        double base = round(unit.convertToBaseUnit(value));
        return Objects.hash(base, unit.getClass());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    
    
    private void validateOperand(Quantity<U> other) {
        if (other == null)
            throw new IllegalArgumentException("Quantity cannot be null");

        if (this.unit == null || other.unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (!this.unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Incompatible measurement categories");

        if (!Double.isFinite(this.value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Invalid numeric value");
    }

    private double roundToTwoDecimalPlaces(double val) {
        return Math.round(val * 100.0) / 100.0;
    }
    
    // Subtract – Implicit Target Unit
    public Quantity<U> subtract(Quantity<U> other) {
        validateOperand(other);

        double thisBase = unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        double baseResult = thisBase - otherBase;

        double resultInThisUnit =
                unit.convertFromBaseUnit(baseResult);

        resultInThisUnit = roundToTwoDecimalPlaces(resultInThisUnit);

        return new Quantity<>(resultInThisUnit, this.unit);
    }
    
    //Subtract – Explicit Target Unit
    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateOperand(other);

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double thisBase = unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        double baseResult = thisBase - otherBase;

        double result =
                targetUnit.convertFromBaseUnit(baseResult);

        result = roundToTwoDecimalPlaces(result);

        return new Quantity<>(result, targetUnit);
    }
    
    // Division (Returns double – Dimensionless)
    public double divide(Quantity<U> other) {
        validateOperand(other);

        double thisBase = unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        if (otherBase == 0.0)
            throw new ArithmeticException("Division by zero");

        return thisBase / otherBase;
    }
}