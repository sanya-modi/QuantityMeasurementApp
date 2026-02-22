package com;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LengthTest {
	 
	@Test
    public void yardToYardEquality() {
        assertEquals(
                new Length(1.0, Length.LengthUnit.YARDS),
                new Length(1.0, Length.LengthUnit.YARDS)
        );
    }

    @Test
    public void yardToFeetEquality() {
        assertEquals(
                new Length(1.0, Length.LengthUnit.YARDS),
                new Length(3.0, Length.LengthUnit.FEET)
        );
    }

    @Test
    public void yardToInchesEquality() {
        assertEquals(
                new Length(1.0, Length.LengthUnit.YARDS),
                new Length(36.0, Length.LengthUnit.INCHES)
        );
    }

    @Test
    public void centimeterToInchesEquality() {
        assertEquals(
                new Length(1.0, Length.LengthUnit.CENTIMETERS),
                new Length(0.393701, Length.LengthUnit.INCHES)
        );
    }

    @Test
    public void transitivePropertyTest() {
        Length yard = new Length(1.0, Length.LengthUnit.YARDS);
        Length feet = new Length(3.0, Length.LengthUnit.FEET);
        Length inches = new Length(36.0, Length.LengthUnit.INCHES);

        assertEquals(yard, feet);
        assertEquals(feet, inches);
        assertEquals(yard, inches);
    }

    @Test
    public void nullComparisonTest() {
        Length yard = new Length(1.0, Length.LengthUnit.YARDS);
        assertNotEquals(yard, null);
    }

    @Test
    public void sameReferenceTest() {
        Length yard = new Length(1.0, Length.LengthUnit.YARDS);
        assertEquals(yard, yard);
    }

    @Test
    public void differentValuesNotEqual() {
        assertNotEquals(
                new Length(1.0, Length.LengthUnit.YARDS),
                new Length(2.0, Length.LengthUnit.YARDS)
        );
    }
	
}
