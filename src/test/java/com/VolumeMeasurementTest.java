package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.lengthmeasurement.LengthUnit;
import com.measurement.Quantity;
import com.volumemeasurement.VolumeUnit;
import com.weightmeasurement.WeightUnit;

public class VolumeMeasurementTest {
	
	 private static final double EPSILON = 0.0001;

	     //  EQUALITY TESTS

	    @Test
	    void testEquality_LitreToLitre_SameValue() {
	        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
	                .equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
	    }

	    @Test
	    void testEquality_LitreToLitre_DifferentValue() {
	        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE)
	                .equals(new Quantity<>(2.0, VolumeUnit.LITRE)));
	    }

	    @Test
	    void testEquality_LitreToMillilitre_EquivalentValue() {
	        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
	                .equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
	    }

	    @Test
	    void testEquality_MillilitreToLitre_EquivalentValue() {
	        assertTrue(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
	                .equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
	    }

	    @Test
	    void testEquality_LitreToGallon_EquivalentValue() {
	        assertTrue(new Quantity<>(3.78541, VolumeUnit.LITRE)
	                .equals(new Quantity<>(1.0, VolumeUnit.GALLON)));
	    }

	    @Test
	    void testEquality_GallonToLitre_EquivalentValue() {
	        assertTrue(new Quantity<>(1.0, VolumeUnit.GALLON)
	                .equals(new Quantity<>(3.78541, VolumeUnit.LITRE)));
	    }

	    @Test
	    void testEquality_ZeroValue() {
	        assertTrue(new Quantity<>(0.0, VolumeUnit.LITRE)
	                .equals(new Quantity<>(0.0, VolumeUnit.MILLILITRE)));
	    }

	    @Test
	    void testEquality_NegativeVolume() {
	        assertTrue(new Quantity<>(-1.0, VolumeUnit.LITRE)
	                .equals(new Quantity<>(-1000.0, VolumeUnit.MILLILITRE)));
	    }

	    @Test
	    void testEquality_LargeVolumeValue() {
	        assertTrue(new Quantity<>(1000000.0, VolumeUnit.MILLILITRE)
	                .equals(new Quantity<>(1000.0, VolumeUnit.LITRE)));
	    }

	    @Test
	    void testEquality_SmallVolumeValue() {
	        assertTrue(new Quantity<>(0.001, VolumeUnit.LITRE)
	                .equals(new Quantity<>(1.0, VolumeUnit.MILLILITRE)));
	    }

	    @Test
	    void testEquality_SameReference() {
	        Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.LITRE);
	        assertTrue(v.equals(v));
	    }

	    @Test
	    void testEquality_NullComparison() {
	        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(null));
	    }

	    @Test
	    void testEquality_VolumeVsLength_Incompatible() {
	        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE)
	                .equals(new Quantity<>(1.0, LengthUnit.FOOT)));
	    }

	    @Test
	    void testEquality_VolumeVsWeight_Incompatible() {
	        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE)
	                .equals(new Quantity<>(1.0, WeightUnit.KILOGRAM)));
	    }

	     //  CONVERSION TESTS

	    @Test
	    void testConversion_LitreToMillilitre() {
	        Quantity<VolumeUnit> result =
	                new Quantity<>(1.0, VolumeUnit.LITRE)
	                        .convertTo(VolumeUnit.MILLILITRE);

	        assertEquals(1000.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testConversion_MillilitreToLitre() {
	        Quantity<VolumeUnit> result =
	                new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
	                        .convertTo(VolumeUnit.LITRE);

	        assertEquals(1.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testConversion_GallonToLitre() {
	        Quantity<VolumeUnit> result =
	                new Quantity<>(1.0, VolumeUnit.GALLON)
	                        .convertTo(VolumeUnit.LITRE);

	        assertEquals(3.78541,
	                VolumeUnit.GALLON.getConversionFactor(),
	                EPSILON);
	    }

	    @Test
	    void testConversion_LitreToGallon() {
	        Quantity<VolumeUnit> result =
	                new Quantity<>(3.78541, VolumeUnit.LITRE)
	                        .convertTo(VolumeUnit.GALLON);

	        assertEquals(1.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testConversion_SameUnit() {
	        Quantity<VolumeUnit> result =
	                new Quantity<>(5.0, VolumeUnit.LITRE)
	                        .convertTo(VolumeUnit.LITRE);

	        assertEquals(5.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testConversion_RoundTrip() {
	        Quantity<VolumeUnit> original =
	                new Quantity<>(1.5, VolumeUnit.LITRE);

	        Quantity<VolumeUnit> roundTrip =
	                original.convertTo(VolumeUnit.MILLILITRE)
	                        .convertTo(VolumeUnit.LITRE);

	        assertEquals(original.getValue(), roundTrip.getValue(), EPSILON);
	    }

	    /* ============================================================
	       ADDITION TESTS
	       ============================================================ */

	    @Test
	    void testAddition_SameUnit_LitrePlusLitre() {
	        Quantity<VolumeUnit> result =
	                new Quantity<>(1.0, VolumeUnit.LITRE)
	                        .add(new Quantity<>(2.0, VolumeUnit.LITRE));

	        assertEquals(3.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testAddition_CrossUnit_LitrePlusMillilitre() {
	        Quantity<VolumeUnit> result =
	                new Quantity<>(1.0, VolumeUnit.LITRE)
	                        .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

	        assertEquals(2.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testAddition_ExplicitTargetUnit_Gallon() {
	        Quantity<VolumeUnit> result =
	                new Quantity<>(3.78541, VolumeUnit.LITRE)
	                        .add(new Quantity<>(3.78541, VolumeUnit.LITRE),
	                                VolumeUnit.GALLON);

	        assertEquals(2.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testAddition_Commutativity() {
	        Quantity<VolumeUnit> a =
	                new Quantity<>(1.0, VolumeUnit.LITRE);
	        Quantity<VolumeUnit> b =
	                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

	        assertEquals(
	                a.add(b).getValue(),
	                b.add(a).convertTo(VolumeUnit.LITRE).getValue(),
	                EPSILON
	        );
	    }

	    @Test
	    void testAddition_WithZero() {
	        Quantity<VolumeUnit> result =
	                new Quantity<>(5.0, VolumeUnit.LITRE)
	                        .add(new Quantity<>(0.0, VolumeUnit.MILLILITRE));

	        assertEquals(5.0, result.getValue(), EPSILON);
	    }

	    @Test
	    void testAddition_NegativeValues() {
	        Quantity<VolumeUnit> result =
	                new Quantity<>(5.0, VolumeUnit.LITRE)
	                        .add(new Quantity<>(-2000.0, VolumeUnit.MILLILITRE));

	        assertEquals(3.0, result.getValue(), EPSILON);
	    }
	    
	    //   ENUM VALIDATION

	    @Test
	    void testVolumeUnitEnum_Constants() {
	    	assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor(), EPSILON);
	    	assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor(), EPSILON);
	        assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor(), EPSILON);
	    }

}
