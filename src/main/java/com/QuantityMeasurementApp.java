package com;

public class QuantityMeasurementApp {
	
	public static void main(String[] args) {
		
		Feet f1 = new Feet(1.0);
		Feet f2 = new Feet(1.0);
		
		System.out.println("Equals: " + f1.equals(f2));
		
		Inches.demonstrateInchesEquality();
		
	}

}
