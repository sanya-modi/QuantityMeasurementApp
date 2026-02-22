package com;

public class QuantityMeasurementApp {

    // Equality API 
    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static boolean demonstrateLengthComparison(
            double value1, Length.LengthUnit unit1,
            double value2, Length.LengthUnit unit2) {

        Length l1 = new Length(value1, unit1);
        Length l2 = new Length(value2, unit2);

        return demonstrateLengthEquality(l1, l2);
    }

    // UC5 NEW STATIC CONVERSION API 
    public static Length demonstrateLengthConversion(
            double value,
            Length.LengthUnit fromUnit,
            Length.LengthUnit toUnit) {

        Length length = new Length(value, fromUnit);
        return length.convertTo(toUnit);
    }

    // Overloaded version
    public static Length demonstrateLengthConversion(
            Length length,
            Length.LengthUnit toUnit) {

        return length.convertTo(toUnit);
    }

    // Standalone testing
    public static void main(String[] args) {

        Length result1 =
                demonstrateLengthConversion(1.0,
                        Length.LengthUnit.FEET,
                        Length.LengthUnit.INCHES);

        System.out.println(result1); // 12.00 INCHES

        Length result2 =
                demonstrateLengthConversion(2.0,
                        Length.LengthUnit.YARDS,
                        Length.LengthUnit.INCHES);

        System.out.println(result2); // 72.00 INCHES
    }
}