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
    
 // -------- Explicit Target = FEET --------
    @Test
    void testAddition_WithExplicitTargetUnit_Feet() {

        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length result = LengthAddition.add(
                l1, l2, Length.LengthUnit.FEET);

        assertEquals(
                new Length(2.0, Length.LengthUnit.FEET),
                result
        );
    }

    // -------- Explicit Target = INCHES --------
    @Test
    void testAddition_WithExplicitTargetUnit_Inches() {

        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length result = LengthAddition.add(
                l1, l2, Length.LengthUnit.INCHES);

        assertEquals(
                new Length(24.0, Length.LengthUnit.INCHES),
                result
        );
    }

    // -------- Explicit Target = YARDS --------
    @Test
    void testAddition_WithExplicitTargetUnit_Yards() {

        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length result = LengthAddition.add(
                l1, l2, Length.LengthUnit.YARDS);

        assertEquals(
                new Length(2.0 / 3.0, Length.LengthUnit.YARDS),
                result
        );
    }

    // -------- Commutativity --------
    @Test
    void testAddition_Commutative_WithExplicitTargetUnit() {

        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length result1 = LengthAddition.add(
                l1, l2, Length.LengthUnit.YARDS);

        Length result2 = LengthAddition.add(
                l2, l1, Length.LengthUnit.YARDS);

        assertEquals(result1, result2);
    }

    // -------- Zero Case --------
    @Test
    void testAddition_WithZero_ExplicitTargetUnit() {

        Length l1 = new Length(5.0, Length.LengthUnit.FEET);
        Length l2 = new Length(0.0, Length.LengthUnit.INCHES);

        Length result = LengthAddition.add(
                l1, l2, Length.LengthUnit.YARDS);

        assertEquals(
                new Length(5.0 / 3.0, Length.LengthUnit.YARDS),
                result
        );
    }

    // -------- Negative Values --------
    @Test
    void testAddition_WithNegativeValues() {

        Length l1 = new Length(5.0, Length.LengthUnit.FEET);
        Length l2 = new Length(-2.0, Length.LengthUnit.FEET);

        Length result = LengthAddition.add(
                l1, l2, Length.LengthUnit.INCHES);

        assertEquals(
                new Length(36.0, Length.LengthUnit.INCHES),
                result
        );
    }

    // -------- Large To Small Scale --------
    @Test
    void testAddition_LargeToSmallScale() {

        Length l1 = new Length(1000.0, Length.LengthUnit.FEET);
        Length l2 = new Length(500.0, Length.LengthUnit.FEET);

        Length result = LengthAddition.add(
                l1, l2, Length.LengthUnit.INCHES);

        assertEquals(
                new Length(18000.0, Length.LengthUnit.INCHES),
                result
        );
    }

    // -------- Small To Large Scale --------
    @Test
    void testAddition_SmallToLargeScale() {

        Length l1 = new Length(12.0, Length.LengthUnit.INCHES);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length result = LengthAddition.add(
                l1, l2, Length.LengthUnit.YARDS);

        assertEquals(
                new Length(2.0 / 3.0, Length.LengthUnit.YARDS),
                result
        );
    }

    // -------- Null Target Unit --------
    @Test
    void testAddition_WithNullTargetUnit_ShouldThrowException() {

        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        assertThrows(
                IllegalArgumentException.class,
                () -> LengthAddition.add(l1, l2, null)
        );
    }

    // -------- Null Operand --------
    @Test
    void testAddition_WithNullOperand_ShouldThrowException() {

        Length l1 = new Length(1.0, Length.LengthUnit.FEET);

        assertThrows(
                IllegalArgumentException.class,
                () -> LengthAddition.add(l1, null,
                        Length.LengthUnit.FEET)
        );
    }
}
