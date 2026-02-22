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

---

## UC3 - Generic Quantity Class for DRY Principle

### Objective
To refactor the existing design by introducing a Generic Quantity class in order to follow the DRY (Don't Repeat Yourself) principle.

### Problem Statement
In previous use cases, separate classes (Feet, Inches) contained similar comparison and conversion logic, leading to code duplication.

The goal of UC3 is to:
- Remove repetitive logic
- Introduce a reusable and generic `Quantity` class
- Centralize comparison logic

### Implementation
- Created a generic `Quantity` class
- Stored value and unit inside the same class
- Implemented common comparison logic in one place
- Eliminated duplicate conversion logic from individual unit classes
- Updated test cases to use the generic class

### Concepts Used
- DRY Principle
- Refactoring
- Abstraction
- Encapsulation
- Code Reusability
- Object-Oriented Design

### Outcome
Successfully reduced code duplication by introducing a single reusable `Quantity` class.  
Improved maintainability and scalability of the system.

🔗 [feature/UC3-GenericLength](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC3-GenericLength/src)

---

## UC4 - Extended Unit Support

### Objective
To extend the Quantity system to support additional measurement units beyond Feet and Inches.

### Problem Statement
The system currently supports limited units for length comparison.  
The goal of this use case is to:

- Add support for more units (e.g., Yard, Centimeter, etc.)
- Ensure accurate comparison between all supported units
- Maintain scalability without increasing code duplication

### Implementation
- Extended the `Quantity` class to support multiple units
- Added unit conversion factors for new measurement types
- Centralized conversion logic inside the generic structure
- Updated equality comparison to handle all supported units
- Added test cases for new unit combinations

### Concepts Used
- Extensibility
- Open-Closed Principle (OCP)
- Abstraction
- Unit Conversion Design
- Scalable Architecture
- Clean Code Practices

### Outcome
Successfully enhanced the system to support multiple measurement units while keeping the design clean and maintainable.  
The application is now more scalable and ready for future unit additions.

🔗 [feature/UC4-YardEquaIity](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC4-YardEquaIity/src)

---

## UC5 - Unit-to-Unit Conversion

### Objective
To implement direct unit-to-unit conversion functionality within the Quantity system.

### Problem Statement
The system currently supports comparison between different units.  
The goal of this use case is to:

- Allow explicit conversion from one unit to another
- Provide accurate converted values
- Ensure conversion logic is reusable and centralized

Example:
- Convert Feet to Inches
- Convert Yard to Feet
- Convert Centimeter to Inch

### Implementation
- Added a conversion mechanism inside the `Quantity` class
- Used standard conversion factors for supported units
- Ensured conversions happen through a base reference unit
- Added test cases to validate conversion accuracy
- Maintained DRY principle by avoiding duplicate conversion logic

### Concepts Used
- Unit Conversion Logic
- Base Unit Strategy
- DRY Principle
- Clean Architecture
- Floating Point Precision Handling
- Unit Testing

### Outcome
Successfully implemented reliable unit-to-unit conversion functionality.  
The system now supports both comparison and direct conversion across multiple units.

🔗 [feature/UC5-UnitConversion](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC5-UnitConversion/src)

---

## UC6 - Addition of Two Length Units

### Objective
To implement functionality that allows addition of two length quantities, even if they are in different units.

### Problem Statement
The system currently supports comparison and conversion of units.  
The goal of this use case is to:

- Add two length quantities
- Handle different units during addition
- Return the correct summed result

Example:
- 1 ft + 12 in
- 2 yd + 1 ft
- 5 cm + 2 in

### Implementation
- Implemented an `add()` method inside the `Quantity` class
- Converted both quantities to a common base unit before performing addition
- Returned the result in a standard or specified unit
- Ensured floating point precision handling
- Added test cases to validate addition logic

### Concepts Used
- Unit Normalization
- Base Unit Strategy
- Arithmetic Operations on Objects
- DRY Principle
- Clean Code Design
- Unit Testing

### Outcome
Successfully implemented addition of two length quantities across different units.  
The system now supports arithmetic operations in addition to comparison and conversion.

🔗 [feature/UC6-UnitAddition](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC6-UnitAddition/src)
