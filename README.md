# Quantity Measurement App

## UC1 - Compare Two Lengths in Feet

### Objective
To verify equality of two length values measured in feet.

### Problem Statement
Given two length values in feet, check whether they are equal.

### Implementation
- Created a `Feet` class
- Stored length value as `double`
- Overridden `equals()` method to compare values
- Written JUnit test cases

### Concepts Used
- OOP
- Encapsulation
- Method Overriding
- Unit Testing

### Outcome
Successfully compared two length objects based on value instead of reference.

🔗 [feature/UC1-FeetEquality](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC1-FeetEquality/src)

---

## UC2 - Compare Length in Feet and Inches

### Objective
To verify equality between length values measured in different units (Feet and Inches).

### Problem Statement
Given two length values where:
- One value is in Feet
- One value is in Inches

The system should correctly determine whether both represent the same physical length.

### Implementation
- Created an `Inches` class
- Stored length value as `double`
- Implemented logic to convert inches to feet (or vice versa) for comparison
- Used overridden `equals()` method for value-based comparison
- Written JUnit test cases for cross-unit validation

### Concepts Used
- OOP
- Encapsulation
- Unit Conversion
- Method Overriding
- Floating Point Comparison
- Unit Testing

### Outcome
Successfully implemented comparison between different measurement units by introducing unit conversion logic.

🔗 [feature/UC2-InchEquality](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC2-InchEquality/src)
