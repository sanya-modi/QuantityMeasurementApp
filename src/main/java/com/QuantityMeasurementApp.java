package com;

public final class QuantityMeasurementApp {

    private QuantityMeasurementApp() {
        // prevent instantiation
    }

    //  EQUALITY 

    public static boolean demonstrateLengthEquality(
            Length l1,
            Length l2) {

        if (l1 == null || l2 == null)
            throw new IllegalArgumentException("Lengths cannot be null");

        return l1.equals(l2);
    }

    // CONVERSION 

    public static Length demonstrateLengthConversion(
            double value,
            Length.LengthUnit fromUnit,
            Length.LengthUnit toUnit) {

        return new Length(value, fromUnit)
                .convertTo(toUnit);
    }

    public static Length demonstrateLengthConversion(
            Length length,
            Length.LengthUnit toUnit) {

        if (length == null)
            throw new IllegalArgumentException("Length cannot be null");

        return length.convertTo(toUnit);
    }

    // ADDITION 
    public static Length demonstrateLengthAddition(
            Length l1,
            Length l2) {

        return LengthAddition.add(l1, l2);
    }

    // Overloaded version (raw values)

    public static Length demonstrateLengthAddition(
            double value1,
            Length.LengthUnit unit1,
            double value2,
            Length.LengthUnit unit2) {

        Length l1 = new Length(value1, unit1);
        Length l2 = new Length(value2, unit2);

        return LengthAddition.add(l1, l2);
    }
    
    //Addition with Target Unit Specification
    public static Length demonstrateLengthAddition(
            Length l1,
            Length l2,
            Length.LengthUnit targetUnit) {

        return LengthAddition.add(l1, l2, targetUnit);
    }


    public static void main(String[] args) {

        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length result = demonstrateLengthAddition(l1, l2);

        System.out.println("Addition Result: " + result);

        Length converted =
                demonstrateLengthConversion(l1,
                        Length.LengthUnit.INCHES);

        System.out.println("Conversion Result: " + converted);

        boolean equal =
                demonstrateLengthEquality(l1,
                        new Length(12.0,
                                Length.LengthUnit.INCHES));

        System.out.println("Equality Result: " + equal);
        
        Length resultInYards =
                demonstrateLengthAddition(
                        l1,
                        l2,
                        Length.LengthUnit.YARDS);

        System.out.println("UC7 Result (Yards): " + resultInYards);
    }
}