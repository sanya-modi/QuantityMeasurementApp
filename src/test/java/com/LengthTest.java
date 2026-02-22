package com;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LengthTest {

    private static final double EPSILON = 1e-6;

    // LengthUnit Enum Tests

    @Test
    void testLengthUnitEnum_FeetConstant() {
        assertEquals(1.0,
                LengthUnit.FEET.getConversionFactor(),
                EPSILON);
    }

    @Test
    void testLengthUnitEnum_InchesConstant() {
        assertEquals(1.0 / 12.0,
                LengthUnit.INCHES.getConversionFactor(),
                EPSILON);
    }

    @Test
    void testLengthUnitEnum_YardsConstant() {
        assertEquals(3.0,
                LengthUnit.YARDS.getConversionFactor(),
                EPSILON);
    }

    @Test
    void testLengthUnitEnum_CentimetersConstant() {
        assertEquals(1.0 / 30.48,
                LengthUnit.CENTIMETERS.getConversionFactor(),
                EPSILON);
    }

    // Convert To Base Unit (Feet)

    @Test
    void testConvertToBaseUnit_FeetToFeet() {
        assertEquals(5.0,
                LengthUnit.FEET.convertToBaseUnit(5.0),
                EPSILON);
    }

    @Test
    void testConvertToBaseUnit_InchesToFeet() {
        assertEquals(1.0,
                LengthUnit.INCHES.convertToBaseUnit(12.0),
                EPSILON);
    }

    @Test
    void testConvertToBaseUnit_YardsToFeet() {
        assertEquals(3.0,
                LengthUnit.YARDS.convertToBaseUnit(1.0),
                EPSILON);
    }

    @Test
    void testConvertToBaseUnit_CentimetersToFeet() {
        assertEquals(1.0,
                LengthUnit.CENTIMETERS.convertToBaseUnit(30.48),
                EPSILON);
    }

    // Convert From Base Unit

    @Test
    void testConvertFromBaseUnit_FeetToFeet() {
        assertEquals(2.0,
                LengthUnit.FEET.convertFromBaseUnit(2.0),
                EPSILON);
    }

    @Test
    void testConvertFromBaseUnit_FeetToInches() {
        assertEquals(12.0,
                LengthUnit.INCHES.convertFromBaseUnit(1.0),
                EPSILON);
    }

    @Test
    void testConvertFromBaseUnit_FeetToYards() {
        assertEquals(1.0,
                LengthUnit.YARDS.convertFromBaseUnit(3.0),
                EPSILON);
    }

    @Test
    void testConvertFromBaseUnit_FeetToCentimeters() {
        assertEquals(30.48,
                LengthUnit.CENTIMETERS.convertFromBaseUnit(1.0),
                EPSILON);
    }
    
 // QuantityLength Simplification Tests

    @Test
    void testQuantityLengthRefactored_Equality() {
        assertEquals(
                new Length(1.0, LengthUnit.FEET),
                new Length(12.0, LengthUnit.INCHES));
    }

    @Test
    void testQuantityLengthRefactored_ConvertTo() {
        Length result =
                new Length(1.0, LengthUnit.FEET)
                        .convertTo(LengthUnit.INCHES);

        assertEquals(
                new Length(12.0, LengthUnit.INCHES),
                result);
    }

    @Test
    void testQuantityLengthRefactored_Add() {
        Length result =
                new Length(1.0, LengthUnit.FEET)
                        .add(new Length(12.0,
                                LengthUnit.INCHES),
                                LengthUnit.FEET);

        assertEquals(
                new Length(2.0, LengthUnit.FEET),
                result);
    }

    @Test
    void testQuantityLengthRefactored_AddWithTargetUnit() {
        Length result =
                new Length(1.0, LengthUnit.FEET)
                        .add(new Length(12.0,
                                LengthUnit.INCHES),
                                LengthUnit.YARDS);

        assertEquals(
                new Length(2.0 / 3.0,
                        LengthUnit.YARDS),
                result);
    }
    
    // Commutativity Property

    @Test
    void testAddition_Commutativity() {

        Length a = new Length(1.0, LengthUnit.FEET);
        Length b = new Length(12.0, LengthUnit.INCHES);

        Length result1 =
                a.add(b, LengthUnit.YARDS);

        Length result2 =
                b.add(a, LengthUnit.YARDS);

        assertEquals(result1, result2);
    }

    // Zero and Negative Tests

    @Test
    void testAddition_WithZero() {
        Length result =
                new Length(5.0, LengthUnit.FEET)
                        .add(new Length(0.0,
                                LengthUnit.INCHES),
                                LengthUnit.FEET);

        assertEquals(
                new Length(5.0, LengthUnit.FEET),
                result);
    }

    @Test
    void testAddition_WithNegativeValues() {
        Length result =
                new Length(5.0, LengthUnit.FEET)
                        .add(new Length(-2.0,
                                LengthUnit.FEET),
                                LengthUnit.INCHES);

        assertEquals(
                new Length(36.0,
                        LengthUnit.INCHES),
                result);
    }
    
 // Validation Tests
    // -------------------------------------------------

    @Test
    void testQuantityLengthRefactored_NullUnit() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Length(1.0, null));
    }

    @Test
    void testQuantityLengthRefactored_InvalidValue() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Length(Double.NaN,
                        LengthUnit.FEET));
    }
    
    // Backward Compatibility Tests

    @Test
    void testBackwardCompatibility_UC1EqualityTests() {
        assertEquals(
                new Length(36.0, LengthUnit.INCHES),
                new Length(1.0, LengthUnit.YARDS));
    }

    @Test
    void testBackwardCompatibility_UC5ConversionTests() {
        Length result =
                new Length(1.0, LengthUnit.YARDS)
                        .convertTo(LengthUnit.INCHES);

        assertEquals(
                new Length(36.0, LengthUnit.INCHES),
                result);
    }

    @Test
    void testBackwardCompatibility_UC6AdditionTests() {
        Length result =
                new Length(1.0, LengthUnit.FEET)
                        .add(new Length(12.0,
                                LengthUnit.INCHES));

        assertEquals(
                new Length(2.0, LengthUnit.FEET),
                result);
    }

    @Test
    void testBackwardCompatibility_UC7AdditionWithTargetUnitTests() {
        Length result =
                new Length(1.0, LengthUnit.FEET)
                        .add(new Length(12.0,
                                LengthUnit.INCHES),
                                LengthUnit.INCHES);

        assertEquals(
                new Length(24.0, LengthUnit.INCHES),
                result);
    }

    // Round Trip Conversion Precision

    @Test
    void testRoundTripConversion_RefactoredDesign() {

        Length original =
                new Length(10.0,
                        LengthUnit.FEET);

        Length converted =
                original.convertTo(
                        LengthUnit.INCHES)
                        .convertTo(
                                LengthUnit.FEET);

        assertEquals(original, converted);
    }
    
    
}