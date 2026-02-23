package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import org.junit.jupiter.api.Test;

import com.lengthmeasurement.LengthUnit;
import com.measurement.Quantity;
import com.weightmeasurement.WeightUnit;

public class QuantityTest {
	
    // LENGTH TESTS (Base = INCH)

    @Test
    void testLengthEquality_FootToInch() {
        Quantity<LengthUnit> oneFoot =
                new Quantity<>(1.0, LengthUnit.FOOT);

        Quantity<LengthUnit> twelveInch =
                new Quantity<>(12.0, LengthUnit.INCH);

        assertEquals(oneFoot, twelveInch);
    }

    @Test
    void testLengthEquality_YardToInch() {
        Quantity<LengthUnit> oneYard =
                new Quantity<>(1.0, LengthUnit.YARD);

        Quantity<LengthUnit> thirtySixInch =
                new Quantity<>(36.0, LengthUnit.INCH);

        assertEquals(oneYard, thirtySixInch);
    }

    @Test
    void testLengthConversion_FootToInch() {
        Quantity<LengthUnit> oneFoot =
                new Quantity<>(1.0, LengthUnit.FOOT);

        Quantity<LengthUnit> result =
                oneFoot.convertTo(LengthUnit.INCH);

        assertEquals(new Quantity<>(12.0, LengthUnit.INCH), result);
    }

    @Test
    void testLengthAddition() {
        Quantity<LengthUnit> oneFoot =
                new Quantity<>(1.0, LengthUnit.FOOT);

        Quantity<LengthUnit> twelveInch =
                new Quantity<>(12.0, LengthUnit.INCH);

        Quantity<LengthUnit> result =
                oneFoot.add(twelveInch, LengthUnit.FOOT);

        assertEquals(new Quantity<>(2.0, LengthUnit.FOOT), result);
    }

    @Test
    void testLengthZero() {
        Quantity<LengthUnit> zero =
                new Quantity<>(0.0, LengthUnit.INCH);

        Quantity<LengthUnit> zeroFoot =
                new Quantity<>(0.0, LengthUnit.FOOT);

        assertEquals(zero, zeroFoot);
    }

    @Test
    void testLengthNegative() {
        Quantity<LengthUnit> minusOneFoot =
                new Quantity<>(-1.0, LengthUnit.FOOT);

        Quantity<LengthUnit> minusTwelveInch =
                new Quantity<>(-12.0, LengthUnit.INCH);

        assertEquals(minusOneFoot, minusTwelveInch);
    }

    //  WEIGHT TESTS (Base = GRAM)=

    @Test
    void testWeightEquality_KgToGram() {
        Quantity<WeightUnit> oneKg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> thousandGram =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(oneKg, thousandGram);
    }

    @Test
    void testWeightEquality_PoundToGram() {
        Quantity<WeightUnit> onePound =
                new Quantity<>(1.0, WeightUnit.POUND);

        Quantity<WeightUnit> grams =
                new Quantity<>(453.59237, WeightUnit.GRAM);

        assertEquals(onePound, grams);
    }

    @Test
    void testWeightConversion_KgToGram() {
        Quantity<WeightUnit> oneKg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> result =
                oneKg.convertTo(WeightUnit.GRAM);

        assertEquals(new Quantity<>(1000.0, WeightUnit.GRAM), result);
    }

    @Test
    void testWeightAddition() {
        Quantity<WeightUnit> oneKg =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> thousandGram =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result =
                oneKg.add(thousandGram, WeightUnit.KILOGRAM);

        assertEquals(new Quantity<>(2.0, WeightUnit.KILOGRAM), result);
    }

    @Test
    void testWeightZero() {
        Quantity<WeightUnit> zero =
                new Quantity<>(0.0, WeightUnit.GRAM);

        Quantity<WeightUnit> zeroKg =
                new Quantity<>(0.0, WeightUnit.KILOGRAM);

        assertEquals(zero, zeroKg);
    }

    @Test
    void testWeightNegative() {
        Quantity<WeightUnit> minusOneKg =
                new Quantity<>(-1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> minusThousandGram =
                new Quantity<>(-1000.0, WeightUnit.GRAM);

        assertEquals(minusOneKg, minusThousandGram);
    }

    // CROSS CATEGORY TESTS

    @Test
    void testCrossCategoryEquality() {
        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FOOT);

        Quantity<WeightUnit> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(length, weight);
    }

    @Test
    void testCrossCategoryAdditionThrowsException() {
        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FOOT);

        Quantity<WeightUnit> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertThrows(IllegalArgumentException.class, () -> {
            length.add((Quantity) weight);
        });
    }

    //  EQUALS CONTRACT TESTS

    @Test
    void testReflexiveProperty() {
        Quantity<WeightUnit> q =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertEquals(q, q);
    }

    @Test
    void testSymmetricProperty() {
        Quantity<WeightUnit> q1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> q2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(q1, q2);
        assertEquals(q2, q1);
    }

    @Test
    void testTransitiveProperty() {
        Quantity<WeightUnit> a =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> b =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> c =
                new Quantity<>(1000000.0, WeightUnit.MILLIGRAM);

        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c);
    }

    @Test
    void testHashCodeConsistency() {
        Quantity<WeightUnit> a =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> b =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testNullComparison() {
        Quantity<WeightUnit> q =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotEquals(q, null);
    }

}
