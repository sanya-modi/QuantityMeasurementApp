package com;

public class LengthAddition {

    private LengthAddition() {
        // prevent instantiation
    }

    public static Length add(Length l1, Length l2) {

        if (l1 == null || l2 == null)
            throw new IllegalArgumentException("Lengths cannot be null");

        // Convert both to base unit (inches)
        double baseSum =
                l1.convertToBaseUnit() + l2.convertToBaseUnit();

        // Convert sum back to first operand's unit
        double result =
                baseSum / l1.getUnit().getConversionFactor();

        return new Length(result, l1.getUnit());
    }
}
