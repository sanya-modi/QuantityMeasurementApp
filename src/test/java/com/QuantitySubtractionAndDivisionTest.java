package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import com.lengthmeasurement.LengthUnit;
import com.measurement.*;
import com.volumemeasurement.VolumeUnit;
import com.weightmeasurement.WeightUnit;

public class QuantitySubtractionAndDivisionTest {
	
	 private static final double EPSILON = 0.0001;

	    //   SUBTRACTION TESTS


	    @Test
	    void testSubtraction_SameUnit_FeetMinusFeet() {
	        Quantity<LengthUnit> result =
	                new Quantity<>(10.0, LengthUnit.FOOT)
	                        .subtract(new Quantity<>(5.0, LengthUnit.FOOT));

	        assertEquals(5.0, result.getValue(), EPSILON);
	        assertEquals(LengthUnit.FOOT, result.getUnit());
	    }

	    @Test
	    void testSubtraction_CrossUnit_FeetMinusInches() {
	        Quantity<LengthUnit> result =
	                new Quantity<>(10.0, LengthUnit.FOOT)
	                        .subtract(new Quantity<>(6.0, LengthUnit.INCH));

	        assertEquals(9.5, result.getValue(), EPSILON);
	    }

	    @Test
	    void testSubtraction_ExplicitTargetUnit_Inches() {
	        Quantity<LengthUnit> result =
	                new Quantity<>(10.0, LengthUnit.FOOT)
	                        .subtract(new Quantity<>(6.0, LengthUnit.INCH),
	                                LengthUnit.INCH);

	        assertEquals(114.0, result.getValue(), EPSILON);
	        assertEquals(LengthUnit.INCH, result.getUnit());
	    }

	    @Test
	    void testSubtraction_ResultingInNegative() {
	        Quantity<LengthUnit> result =
	                new Quantity<>(5.0, LengthUnit.FOOT)
	                        .subtract(new Quantity<>(10.0, LengthUnit.FOOT));

	        assertEquals(-5.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testSubtraction_ResultingInZero() {
	        Quantity<LengthUnit> result =
	                new Quantity<>(10.0, LengthUnit.FOOT)
	                        .subtract(new Quantity<>(120.0, LengthUnit.INCH));

	        assertEquals(0.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testSubtraction_NonCommutative() {
	        Quantity<LengthUnit> a =
	                new Quantity<>(10.0, LengthUnit.FOOT);
	        Quantity<LengthUnit> b =
	                new Quantity<>(5.0, LengthUnit.FOOT);

	        assertNotEquals(a.subtract(b).getValue(),
	                        b.subtract(a).getValue());
	    }

	    @Test
	    void testSubtraction_ChainedOperations() {
	        Quantity<LengthUnit> result =
	                new Quantity<>(10.0, LengthUnit.FOOT)
	                        .subtract(new Quantity<>(2.0, LengthUnit.FOOT))
	                        .subtract(new Quantity<>(1.0, LengthUnit.FOOT));

	        assertEquals(7.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testSubtraction_NullOperand() {
	        assertThrows(IllegalArgumentException.class,
	                () -> new Quantity<>(10.0, LengthUnit.FOOT)
	                        .subtract(null));
	    }

	    @Test
	    void testSubtraction_CrossCategory() {
	        Quantity rawLength = new Quantity<>(10.0, LengthUnit.FOOT);
	        Quantity rawWeight = new Quantity<>(5.0, WeightUnit.KILOGRAM);

	        assertThrows(IllegalArgumentException.class,
	                () -> rawLength.subtract(rawWeight));
	    }

	    @Test
	    void testSubtraction_AllCategories() {
	        Quantity<WeightUnit> weight =
	                new Quantity<>(10.0, WeightUnit.KILOGRAM)
	                        .subtract(new Quantity<>(5000.0, WeightUnit.GRAM));

	        assertEquals(5.0, weight.getValue(), EPSILON);

	        Quantity<VolumeUnit> volume =
	                new Quantity<>(5.0, VolumeUnit.LITRE)
	                        .subtract(new Quantity<>(500.0, VolumeUnit.MILLILITRE));

	        assertEquals(4.5, volume.getValue(), EPSILON);
	    }

	    @Test
	    void testSubtraction_Immutability() {
	        Quantity<LengthUnit> original =
	                new Quantity<>(10.0, LengthUnit.FOOT);

	        original.subtract(new Quantity<>(5.0, LengthUnit.FOOT));

	        assertEquals(10.0, original.getValue(), EPSILON);
	    }

	    @Test
	    void testSubtractionAddition_Inverse() {
	        Quantity<LengthUnit> a =
	                new Quantity<>(10.0, LengthUnit.FOOT);
	        Quantity<LengthUnit> b =
	                new Quantity<>(2.0, LengthUnit.FOOT);

	        Quantity<LengthUnit> result =
	                a.add(b).subtract(b);

	        assertEquals(a.getValue(), result.getValue(), EPSILON);
	    }

	    //   DIVISION TESTS

	    @Test
	    void testDivision_SameUnit() {
	        double result =
	                new Quantity<>(10.0, LengthUnit.FOOT)
	                        .divide(new Quantity<>(2.0, LengthUnit.FOOT));

	        assertEquals(5.0, result, EPSILON);
	    }

	    @Test
	    void testDivision_CrossUnit() {
	        double result =
	                new Quantity<>(24.0, LengthUnit.INCH)
	                        .divide(new Quantity<>(2.0, LengthUnit.FOOT));

	        assertEquals(1.0, result, EPSILON);
	    }

	    @Test
	    void testDivision_RatioGreaterThanOne() {
	        double result =
	                new Quantity<>(10.0, WeightUnit.KILOGRAM)
	                        .divide(new Quantity<>(5.0, WeightUnit.KILOGRAM));

	        assertEquals(2.0, result, EPSILON);
	    }

	    @Test
	    void testDivision_RatioLessThanOne() {
	        double result =
	                new Quantity<>(5.0, VolumeUnit.LITRE)
	                        .divide(new Quantity<>(10.0, VolumeUnit.LITRE));

	        assertEquals(0.5, result, EPSILON);
	    }

	    @Test
	    void testDivision_RatioEqualToOne() {
	        double result =
	                new Quantity<>(10.0, LengthUnit.FOOT)
	                        .divide(new Quantity<>(10.0, LengthUnit.FOOT));

	        assertEquals(1.0, result, EPSILON);
	    }

	    @Test
	    void testDivision_NonCommutative() {
	        double a =
	                new Quantity<>(10.0, LengthUnit.FOOT)
	                        .divide(new Quantity<>(5.0, LengthUnit.FOOT));

	        double b =
	                new Quantity<>(5.0, LengthUnit.FOOT)
	                        .divide(new Quantity<>(10.0, LengthUnit.FOOT));

	        assertNotEquals(a, b);
	    }

	    @Test
	    void testDivision_ByZero() {
	        assertThrows(ArithmeticException.class,
	                () -> new Quantity<>(10.0, LengthUnit.FOOT)
	                        .divide(new Quantity<>(0.0, LengthUnit.FOOT)));
	    }

	    @Test
	    void testDivision_CrossCategory() {

	        Quantity length = new Quantity<>(10.0, LengthUnit.FOOT);
	        Quantity weight = new Quantity<>(5.0, WeightUnit.KILOGRAM);

	        assertThrows(IllegalArgumentException.class,
	                () -> length.divide(weight));
	    }

	    @Test
	    void testDivision_LargeRatio() {
	        double result =
	                new Quantity<>(1e6, WeightUnit.KILOGRAM)
	                        .divide(new Quantity<>(1.0, WeightUnit.KILOGRAM));

	        assertEquals(1e6, result, EPSILON);
	    }

	    @Test
	    void testDivision_SmallRatio() {
	        double result =
	                new Quantity<>(1.0, WeightUnit.KILOGRAM)
	                        .divide(new Quantity<>(1e6, WeightUnit.KILOGRAM));

	        assertEquals(1e-6, result, EPSILON);
	    }

	    @Test
	    void testDivision_Immutability() {
	        Quantity<LengthUnit> original =
	                new Quantity<>(10.0, LengthUnit.FOOT);

	        original.divide(new Quantity<>(2.0, LengthUnit.FOOT));

	        assertEquals(10.0, original.getValue(), EPSILON);
	    }

	    @Test
	    void testDivision_Associativity() {
	        Quantity<LengthUnit> A =
	                new Quantity<>(20.0, LengthUnit.FOOT);
	        Quantity<LengthUnit> B =
	                new Quantity<>(5.0, LengthUnit.FOOT);
	        Quantity<LengthUnit> C =
	                new Quantity<>(2.0, LengthUnit.FOOT);

	        double left = A.divide(B) / C.getValue();
	        double right = A.getValue() / (B.divide(C));

	        assertNotEquals(left, right);
	    }

	    @Test
	    void testSubtractionAndDivision_Integration() {
	        double result =
	                new Quantity<>(10.0, LengthUnit.FOOT)
	                        .subtract(new Quantity<>(2.0, LengthUnit.FOOT))
	                        .divide(new Quantity<>(2.0, LengthUnit.FOOT));

	        assertEquals(4.0, result, EPSILON);
	    }

}
