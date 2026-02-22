package com;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LengthAdditionTest {

    private static final double EPSILON = 1e-6;

    //  SAME UNIT 

    @Test
    public void testAddition_SameUnit_FeetPlusFeet() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(2.0, Length.LengthUnit.FEET);

        Length result = LengthAddition.add(l1, l2);

        assertTrue(result.equals(new Length(3.0, Length.LengthUnit.FEET)));
    }

    @Test
    public void testAddition_SameUnit_InchPlusInch() {
        Length l1 = new Length(6.0, Length.LengthUnit.INCHES);
        Length l2 = new Length(6.0, Length.LengthUnit.INCHES);

        Length result = LengthAddition.add(l1, l2);

        assertTrue(result.equals(new Length(12.0, Length.LengthUnit.INCHES)));
    }

    //  CROSS UNIT 

    @Test
    public void testAddition_CrossUnit_FeetPlusInches() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length result = LengthAddition.add(l1, l2);

        assertTrue(result.equals(new Length(2.0, Length.LengthUnit.FEET)));
    }

    @Test
    public void testAddition_CrossUnit_InchesPlusFeet() {
        Length l1 = new Length(12.0, Length.LengthUnit.INCHES);
        Length l2 = new Length(1.0, Length.LengthUnit.FEET);

        Length result = LengthAddition.add(l1, l2);

        assertTrue(result.equals(new Length(24.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void testAddition_CrossUnit_YardPlusFeet() {
        Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
        Length l2 = new Length(3.0, Length.LengthUnit.FEET);

        Length result = LengthAddition.add(l1, l2);

        assertTrue(result.equals(new Length(2.0, Length.LengthUnit.YARDS)));
    }

    @Test
    public void testAddition_CrossUnit_CentimeterPlusInch() {
        Length l1 = new Length(2.54, Length.LengthUnit.CENTIMETERS);
        Length l2 = new Length(1.0, Length.LengthUnit.INCHES);

        Length result = LengthAddition.add(l1, l2);

        // 2.54 cm = 1 inch → total = 2 inches → 5.08 cm
        assertTrue(result.equals(new Length(5.08, Length.LengthUnit.CENTIMETERS)));
    }

    //  COMMUTATIVITY 

    @Test
    public void testAddition_Commutativity() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length r1 = LengthAddition.add(l1, l2);
        Length r2 = LengthAddition.add(l2, l1)
                                   .convertTo(Length.LengthUnit.FEET);

        assertTrue(r1.equals(r2));
    }

    // ================= IDENTITY =================

    @Test
    public void testAddition_WithZero() {
        Length l1 = new Length(5.0, Length.LengthUnit.FEET);
        Length l2 = new Length(0.0, Length.LengthUnit.INCHES);

        Length result = LengthAddition.add(l1, l2);

        assertTrue(result.equals(l1));
    }

    // NEGATIVE 

    @Test
    public void testAddition_NegativeValues() {
        Length l1 = new Length(5.0, Length.LengthUnit.FEET);
        Length l2 = new Length(-2.0, Length.LengthUnit.FEET);

        Length result = LengthAddition.add(l1, l2);

        assertTrue(result.equals(new Length(3.0, Length.LengthUnit.FEET)));
    }

    //  LARGE VALUES 

    @Test
    public void testAddition_LargeValues() {
        Length l1 = new Length(1e6, Length.LengthUnit.FEET);
        Length l2 = new Length(1e6, Length.LengthUnit.FEET);

        Length result = LengthAddition.add(l1, l2);

        assertTrue(result.equals(new Length(2e6, Length.LengthUnit.FEET)));
    }

    //  SMALL VALUES 

    @Test
    public void testAddition_SmallValues() {
        Length l1 = new Length(0.001, Length.LengthUnit.FEET);
        Length l2 = new Length(0.002, Length.LengthUnit.FEET);

        Length result = LengthAddition.add(l1, l2);

        assertTrue(result.equals(new Length(0.003, Length.LengthUnit.FEET)));
    }

    //  NULL HANDLING 

    @Test
    public void testAddition_NullSecondOperand() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> LengthAddition.add(l1, null));
    }

    @Test
    public void testAddition_NullFirstOperand() {
        Length l2 = new Length(1.0, Length.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> LengthAddition.add(null, l2));
    }
}
