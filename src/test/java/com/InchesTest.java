package com;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InchesTest {
	
	 @Test
	    public void testEquality_SameValue() {
	        Inches i1 = new Inches(5.0);
	        Inches i2 = new Inches(5.0);
	        assertTrue(i1.equals(i2));
	    }

	    @Test
	    public void testEquality_DifferentValue() {
	        Inches i1 = new Inches(5.0);
	        Inches i2 = new Inches(6.0);
	        assertFalse(i1.equals(i2));
	    }

	    @Test
	    public void testEquality_NullComparison() {
	        Inches i1 = new Inches(5.0);
	        assertFalse(i1.equals(null));
	    }

	    @Test
	    public void testEquality_SameReference() {
	        Inches i1 = new Inches(5.0);
	        assertTrue(i1.equals(i1));
	    }

	    @Test
	    public void testEquality_DifferentClass() {
	        Inches i1 = new Inches(5.0);
	        Feet f1 = new Feet(5.0);
	        assertFalse(i1.equals(f1));
	    }

}
