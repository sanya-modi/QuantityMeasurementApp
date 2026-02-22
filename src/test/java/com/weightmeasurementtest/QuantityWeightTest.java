package com.weightmeasurementtest;

import org.junit.jupiter.api.Test;
import com.weightmeasurement.QuantityWeight;
import com.weightmeasurement.WeightUnit;

import static org.junit.jupiter.api.Assertions.*;

class QuantityWeightTest {

	 private static final double EPSILON = 1e-3;

	    //  Equality Tests 

	    @Test
	    void testEquality_KilogramToKilogram_SameValue() {
	        assertEquals(
	                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
	                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
	        );
	    }

	    @Test
	    void testEquality_KilogramToKilogram_DifferentValue() {
	        assertNotEquals(
	                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
	                new QuantityWeight(2.0, WeightUnit.KILOGRAM)
	        );
	    }

	    @Test
	    void testEquality_GramToGram() {
	        assertEquals(
	                new QuantityWeight(500.0, WeightUnit.GRAM),
	                new QuantityWeight(500.0, WeightUnit.GRAM)
	        );
	    }

	    @Test
	    void testEquality_PoundToPound() {
	        assertEquals(
	                new QuantityWeight(2.0, WeightUnit.POUND),
	                new QuantityWeight(2.0, WeightUnit.POUND)
	        );
	    }

	    @Test
	    void testEquality_KilogramToGram() {
	        assertEquals(
	                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
	                new QuantityWeight(1000.0, WeightUnit.GRAM)
	        );
	    }

	    @Test
	    void testEquality_GramToKilogram() {
	        assertEquals(
	                new QuantityWeight(1000.0, WeightUnit.GRAM),
	                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
	        );
	    }

	    @Test
	    void testEquality_KilogramToPound() {
	        assertEquals(
	                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
	                new QuantityWeight(2.20462262185, WeightUnit.POUND)
	        );
	    }

	    @Test
	    void testEquality_GramToPound() {
	        assertEquals(
	                new QuantityWeight(453.59237, WeightUnit.GRAM),
	                new QuantityWeight(1.0, WeightUnit.POUND)
	        );
	    }

	    @Test
	    void testEquality_ZeroValue() {
	        assertEquals(
	                new QuantityWeight(0.0, WeightUnit.KILOGRAM),
	                new QuantityWeight(0.0, WeightUnit.GRAM)
	        );
	    }

	    @Test
	    void testEquality_NegativeValue() {
	        assertEquals(
	                new QuantityWeight(-1.0, WeightUnit.KILOGRAM),
	                new QuantityWeight(-1000.0, WeightUnit.GRAM)
	        );
	    }

	    @Test
	    void testEquality_LargeValue() {
	        assertEquals(
	                new QuantityWeight(1000000.0, WeightUnit.GRAM),
	                new QuantityWeight(1000.0, WeightUnit.KILOGRAM)
	        );
	    }

	    @Test
	    void testEquality_SmallValue() {
	        assertEquals(
	                new QuantityWeight(0.001, WeightUnit.KILOGRAM),
	                new QuantityWeight(1.0, WeightUnit.GRAM)
	        );
	    }

	    @Test
	    void testEquality_SameReference() {
	        QuantityWeight w = new QuantityWeight(5.0, WeightUnit.GRAM);
	        assertEquals(w, w);
	    }

	    @Test
	    void testEquality_NullComparison() {
	        QuantityWeight w = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
	        assertNotEquals(w, null);
	    }

	    @Test
	    void testEquality_TransitiveProperty() {
	        QuantityWeight a = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
	        QuantityWeight b = new QuantityWeight(1000.0, WeightUnit.GRAM);
	        QuantityWeight c = new QuantityWeight(2.20462262185, WeightUnit.POUND);

	        assertEquals(a, b);
	        assertEquals(b, c);
	        assertEquals(a, c);
	    }

	    @Test
	    void testEquality_HashCodeConsistency() {
	        QuantityWeight a = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
	        QuantityWeight b = new QuantityWeight(1000.0, WeightUnit.GRAM);

	        assertEquals(a, b);
	        assertEquals(a.hashCode(), b.hashCode());
	    }

	    //  Conversion Tests 

	    @Test
	    void testConversion_KilogramToGram() {
	        QuantityWeight result =
	                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
	                        .convertTo(WeightUnit.GRAM);

	        assertEquals(1000.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testConversion_PoundToKilogram() {
	        QuantityWeight result =
	                new QuantityWeight(2.20462262185, WeightUnit.POUND)
	                        .convertTo(WeightUnit.KILOGRAM);

	        assertEquals(1.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testConversion_KilogramToPound() {
	        QuantityWeight result =
	                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
	                        .convertTo(WeightUnit.POUND);

	        assertEquals(2.20462262185, result.getValue(), EPSILON);
	    }

	    @Test
	    void testConversion_SameUnit() {
	        QuantityWeight result =
	                new QuantityWeight(5.0, WeightUnit.GRAM)
	                        .convertTo(WeightUnit.GRAM);

	        assertEquals(5.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testConversion_RoundTrip() {
	        QuantityWeight result =
	                new QuantityWeight(1.5, WeightUnit.KILOGRAM)
	                        .convertTo(WeightUnit.GRAM)
	                        .convertTo(WeightUnit.KILOGRAM);

	        assertEquals(1.5, result.getValue(), EPSILON);
	    }

	    //  Addition Tests 

	    @Test
	    void testAddition_SameUnit() {
	        QuantityWeight result =
	                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
	                        .add(new QuantityWeight(2.0, WeightUnit.KILOGRAM));

	        assertEquals(3.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testAddition_CrossUnit() {
	        QuantityWeight result =
	                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
	                        .add(new QuantityWeight(1000.0, WeightUnit.GRAM));

	        assertEquals(2.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testAddition_ExplicitTargetUnit() {
	        QuantityWeight result =
	                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
	                        .add(new QuantityWeight(1000.0, WeightUnit.GRAM),
	                                WeightUnit.GRAM);

	        assertEquals(2000.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testAddition_Commutativity() {
	        QuantityWeight a =
	                new QuantityWeight(1.0, WeightUnit.KILOGRAM);
	        QuantityWeight b =
	                new QuantityWeight(1000.0, WeightUnit.GRAM);

	        assertEquals(a.add(b), b.add(a, WeightUnit.KILOGRAM));
	    }

	    @Test
	    void testAddition_WithZero() {
	        QuantityWeight result =
	                new QuantityWeight(5.0, WeightUnit.KILOGRAM)
	                        .add(new QuantityWeight(0.0, WeightUnit.GRAM));

	        assertEquals(5.0, result.getValue(), EPSILON);
	    }
	    
}
