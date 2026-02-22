package com.lengthmeasurement;

public final class QuantityMeasurementApp {

    private QuantityMeasurementApp() {}

    // Equality
    public static boolean demonstrateLengthEquality(
            Length l1,
            Length l2) {

        if (l1 == null || l2 == null)
            throw new IllegalArgumentException("Lengths cannot be null");

        return l1.equals(l2);
    }

    // Conversion
    public static Length demonstrateLengthConversion(
            double value,
            LengthUnit fromUnit,
            LengthUnit toUnit) {

        return new Length(value, fromUnit)
                .convertTo(toUnit);
    }

    public static Length demonstrateLengthConversion(
            Length length,
            LengthUnit toUnit) {

        if (length == null)
            throw new IllegalArgumentException("Length cannot be null");

        return length.convertTo(toUnit);
    }

    // UC6 Addition
    public static Length demonstrateLengthAddition(
            Length l1,
            Length l2) {

        return l1.add(l2);
    }

    // UC7 Addition
    public static Length demonstrateLengthAddition(
            Length l1,
            Length l2,
            LengthUnit targetUnit) {

        return l1.add(l2, targetUnit);
    }

    public static void main(String[] args) {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        System.out.println(l1.convertTo(LengthUnit.INCHES));
        System.out.println(l1.add(l2, LengthUnit.FEET));
        System.out.println(new Length(36.0, LengthUnit.INCHES)
                .equals(new Length(1.0, LengthUnit.YARDS)));
    }
}