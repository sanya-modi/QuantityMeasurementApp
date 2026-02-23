package com;

import com.measurement.IMeasurable;
import com.measurement.Quantity;

public class QuantityMeasurementApp {

	 public static <U extends IMeasurable> boolean demonstrateEquality(
	            Quantity<U> q1, Quantity<U> q2) {
	        return q1.equals(q2);
	    }

	    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(
	            Quantity<U> quantity, U targetUnit) {
	        return quantity.convertTo(targetUnit);
	    }

	    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(
	            Quantity<U> q1, Quantity<U> q2) {
	        return q1.add(q2);
	    }

	    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(
	            Quantity<U> q1, Quantity<U> q2, U targetUnit) {
	        return q1.add(q2, targetUnit);
	    }
}