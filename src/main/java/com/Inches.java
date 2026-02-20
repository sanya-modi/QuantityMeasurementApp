package com;

public class Inches {
	
	 private final Double value;

     public Inches(Double value) {
         if (value == null)
             throw new IllegalArgumentException("Inches value cannot be null");
         if (value.isNaN())
             throw new IllegalArgumentException("Inches value must be numeric");
         this.value = value;
     }

     @Override
     public boolean equals(Object obj) {

         if (this == obj) return true;
         if (obj == null) return false;
         if (getClass() != obj.getClass()) return false;

         Inches other = (Inches) obj;

         return Double.compare(this.value, other.value) == 0;
     }


 // Static method to demonstrate Inches equality
 public static void demonstrateInchesEquality() {
     Inches i1 = new Inches(1.0);
     Inches i2 = new Inches(1.0);

     System.out.println("1.0 inch and 1.0 inch Equal? " + i1.equals(i2));
 }

}
