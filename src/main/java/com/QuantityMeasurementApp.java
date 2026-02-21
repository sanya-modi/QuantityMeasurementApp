package com;

public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static void main(String[] args) {

        Length length1 = new Length(1.0, Length.LengthUnit.FEET);
        Length length2 = new Length(12.0, Length.LengthUnit.INCHES);

        System.out.println("Are lengths equal? " + length1.equals(length2));

        Length length3 = new Length(1.0, Length.LengthUnit.INCHES);
        Length length4 = new Length(1.0, Length.LengthUnit.INCHES);

        System.out.println("Are lengths equal? " + length3.equals(length4));
    }
}
