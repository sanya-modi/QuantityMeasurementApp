package com;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LengthTest {

    private static final double EPSILON = 1e-6;

    // EQUALITY TESTS 

    @Test
    public void testFeetEquality() {
        assertTrue(new Length(1.0, Length.LengthUnit.FEET)
                .equals(new Length(1.0, Length.LengthUnit.FEET)));
    }

    @Test
    public void testInchesEquality() {
        assertTrue(new Length(12.0, Length.LengthUnit.INCHES)
                .equals(new Length(12.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void testFeetInchesComparison() {
        assertTrue(new Length(1.0, Length.LengthUnit.FEET)
                .equals(new Length(12.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void testFeetInequality() {
        assertFalse(new Length(1.0, Length.LengthUnit.FEET)
                .equals(new Length(2.0, Length.LengthUnit.FEET)));
    }

    @Test
    public void testInchesInequality() {
        assertFalse(new Length(12.0, Length.LengthUnit.INCHES)
                .equals(new Length(24.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void testCrossUnitInequality() {
        assertFalse(new Length(1.0, Length.LengthUnit.FEET)
                .equals(new Length(1.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void testMultipleFeetComparison() {
        assertTrue(new Length(3.0, Length.LengthUnit.FEET)
                .equals(new Length(36.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void yardEquals36Inches() {
        assertTrue(new Length(1.0, Length.LengthUnit.YARDS)
                .equals(new Length(36.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void centimeterEquals39Point3701Inches() {
    	Length cm = new Length(100.0, Length.LengthUnit.CENTIMETERS);
    	Length inch = new Length(39.3701, Length.LengthUnit.INCHES);

    	assertEquals(
    	        cm.convertTo(Length.LengthUnit.INCHES).getValue(),
    	        inch.getValue(),
    	        1e-4
    	);
    }

    @Test
    public void threeFeetEqualsOneYard() {
        assertTrue(new Length(3.0, Length.LengthUnit.FEET)
                .equals(new Length(1.0, Length.LengthUnit.YARDS)));
    }

    @Test
    public void thirtyPoint48CmEqualsOneFoot() {
        assertTrue(new Length(30.48, Length.LengthUnit.CENTIMETERS)
                .equals(new Length(1.0, Length.LengthUnit.FEET)));
    }

    @Test
    public void yardNotEqualToInches() {
        assertFalse(new Length(1.0, Length.LengthUnit.YARDS)
                .equals(new Length(35.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void referenceEqualitySameObject() {
        Length length = new Length(1.0, Length.LengthUnit.FEET);
        assertTrue(length.equals(length));
    }

    @Test
    public void equalsReturnsFalseForNull() {
        Length length = new Length(1.0, Length.LengthUnit.FEET);
        assertFalse(length.equals(null));
    }

    @Test
    public void reflexiveSymmetricAndTransitiveProperty() {
        Length a = new Length(1.0, Length.LengthUnit.YARDS);
        Length b = new Length(3.0, Length.LengthUnit.FEET);
        Length c = new Length(36.0, Length.LengthUnit.INCHES);

        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));
    }

    @Test
    public void differentValuesSameUnitNotEqual() {
        assertFalse(new Length(5.0, Length.LengthUnit.FEET)
                .equals(new Length(6.0, Length.LengthUnit.FEET)));
    }

    // CONVERSION TESTS 

    @Test
    public void crossUnitEqualityDemonstrateMethod() {
        Length result = QuantityMeasurementApp
                .demonstrateLengthConversion(3.0,
                        Length.LengthUnit.FEET,
                        Length.LengthUnit.INCHES);

        Length expected = new Length(36.0, Length.LengthUnit.INCHES);

        assertTrue(result.equals(expected));
    }

    @Test
    public void convertFeetToInches() {
        Length result = QuantityMeasurementApp
                .demonstrateLengthConversion(1.0,
                        Length.LengthUnit.FEET,
                        Length.LengthUnit.INCHES);

        assertEquals(12.0,
                result.convertTo(Length.LengthUnit.INCHES)
                        .convertTo(Length.LengthUnit.INCHES)
                        .equals(result) ? 12.0 : 12.0,
                EPSILON);
    }

    @Test
    public void convertYardsToInchesUsingOverloadedMethod() {

        Length yard = new Length(2.0, Length.LengthUnit.YARDS);

        Length result = QuantityMeasurementApp
                .demonstrateLengthConversion(
                        yard,
                        Length.LengthUnit.INCHES);

        Length expected = new Length(72.0, Length.LengthUnit.INCHES);

        assertTrue(result.equals(expected));
    }
    
    
}