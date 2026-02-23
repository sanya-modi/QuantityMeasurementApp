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

---

## UC7 - Addition with Target Unit Specification

### Objective
To enhance the addition functionality by allowing the result to be returned in a specified target unit.

### Problem Statement
The system currently supports addition of two length quantities.  
The goal of this use case is to:

- Add two quantities with different units
- Allow the user to specify the desired output unit
- Return the final result in the requested unit

Example:
- 1 ft + 12 in → result in ft
- 2 yd + 1 ft → result in in
- 5 cm + 2 in → result in cm

### Implementation
- Extended the `add()` method to accept a target unit parameter
- Converted both input quantities to a base unit
- Performed addition in base unit
- Converted the final result to the specified target unit
- Added test cases for different target unit scenarios

### Concepts Used
- Method Overloading / Parameterized Methods
- Base Unit Strategy
- Flexible API Design
- Clean Architecture
- Reusability
- Unit Testing

### Outcome
Successfully implemented addition with target unit specification.  
The system now provides flexible and user-controlled arithmetic operations across different measurement units.

🔗 [feature/UC7-TargetUnitAddition](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC7-TargetUnitAddition/src)

---

## UC8 - Refactoring Unit Enum to Standalone

### Objective
To refactor the design by extracting the `Unit` enum into a standalone component for better maintainability and separation of concerns.

### Problem Statement
Previously, unit definitions and conversion logic were tightly coupled within the `Quantity` class.  
This created reduced flexibility and limited reusability.

The goal of this use case is to:

- Move the `Unit` enum into a separate standalone structure
- Encapsulate unit-specific conversion logic inside the enum
- Improve modularity and maintainability
- Follow clean architecture principles

### Implementation
- Extracted `Unit` enum into a separate file
- Moved conversion factors inside the enum
- Delegated conversion responsibility to the `Unit` enum
- Reduced responsibility of the `Quantity` class
- Updated test cases to validate refactored structure

### Concepts Used
- Refactoring
- Separation of Concerns
- Single Responsibility Principle (SRP)
- Enum-based Design
- Clean Architecture
- Maintainable Code Structure

### Outcome
Successfully refactored the unit handling mechanism into a standalone enum.  
The system is now more modular, extensible, and aligned with solid design principles.

🔗 [feature/UC8-StandaloneUnit](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC8-StandaloneUnit/src)

---

## UC9 - Weight Measurement

### Objective
To extend the Quantity Measurement system to support weight units in addition to length units.

### Problem Statement
Until now, the system handled only length measurements.  
The goal of this use case is to:

- Introduce weight measurement support
- Add weight units such as Gram and Kilogram
- Ensure accurate comparison and conversion within weight units
- Prevent invalid comparisons between different measurement types (e.g., Length vs Weight)

### Implementation
- Added new weight units (e.g., Gram, Kilogram) in the Unit enum
- Defined appropriate conversion factors for weight units
- Ensured weight conversions are handled through base unit strategy
- Implemented validation to restrict cross-type comparison (Length ≠ Weight)
- Added test cases for weight comparison and conversion

### Concepts Used
- Domain Extension
- Type Safety
- Enum Enhancement
- Separation of Measurement Types
- Base Unit Strategy
- Clean Architecture
- Unit Testing

### Outcome
Successfully extended the system to support weight measurements.  
The application now handles multiple measurement categories while maintaining type safety and design consistency.

🔗 [feature/UC9-WeightMeasurement](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC9-WeightMeasurement/src)

---

## UC10 - Generic Quantity Class with Unit Interface for Multi-Category Support

### Objective
To refactor the system by introducing a `Unit` interface that enables true generic support for multiple measurement categories such as Length and Weight.

### Problem Statement
With the introduction of multiple measurement types (Length and Weight), the existing design needed better abstraction to:

- Support multiple categories cleanly
- Avoid tight coupling between Quantity and specific Unit implementations
- Enable future extension (e.g., Volume, Temperature)
- Maintain type safety and scalability

### Implementation
- Introduced a `Unit` interface to define common unit behaviors
- Implemented category-specific enums (e.g., LengthUnit, WeightUnit) that implement the `Unit` interface
- Updated the `Quantity` class to work with the `Unit` interface instead of a specific enum
- Ensured conversion logic is delegated to respective unit implementations
- Maintained type safety to prevent cross-category operations (Length ≠ Weight)
- Updated test cases to validate multi-category behavior

### Concepts Used
- Interface-based Design
- Polymorphism
- Dependency Inversion Principle (DIP)
- Separation of Concerns
- Scalable Architecture
- Type Safety
- Clean Code Practices

### Outcome
Successfully refactored the system into a fully generic and extensible measurement framework.  
The application now supports multiple measurement categories using interface-driven design, making it scalable for future enhancements.

🔗 [feature/UC10-MultiCategoryUnit](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC10-MultiCategoryUnit/src)

---

## UC11 - Volume Measurement Equality, Conversion, and Addition (Litre, Millilitre, Gallon)

### Objective
To extend the system to support volume measurements including equality comparison, unit conversion, and addition operations for Litre, Millilitre, and Gallon.

### Problem Statement
The system currently supports Length and Weight categories.  
The goal of this use case is to:

- Introduce Volume as a new measurement category
- Support units such as Litre (L), Millilitre (mL), and Gallon (gal)
- Enable equality comparison across volume units
- Allow conversion between supported volume units
- Implement addition of volume quantities
- Maintain strict type safety between categories (Volume ≠ Length ≠ Weight)

### Implementation
- Added a new VolumeUnit enum implementing the `Unit` interface
- Defined appropriate conversion factors using a base unit strategy (e.g., Litre as base)
- Implemented equality comparison across volume units
- Enabled unit-to-unit conversion for volume measurements
- Implemented addition of two volume quantities
- Ensured cross-category operations are restricted
- Added comprehensive test cases for equality, conversion, and addition

### Concepts Used
- Multi-Category Architecture
- Interface-Based Design
- Base Unit Strategy
- Polymorphism
- Type Safety
- Open-Closed Principle (OCP)
- Clean Code Practices
- Unit Testing

### Outcome
Successfully extended the system to support volume measurements including equality, conversion, and arithmetic operations.  
The application now functions as a scalable, multi-category measurement framework supporting Length, Weight, and Volume with consistent architecture.

🔗 [feature/UC11-VolumeMeasurement](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC11-VolumeMeasurement/src)

---

## UC12 - Subtraction and Division Operations on Quantity Measurements

### Objective
To extend the Quantity system by implementing subtraction and division operations across supported measurement categories (Length, Weight, Volume).

### Problem Statement
The system currently supports equality, conversion, addition, and multi-category handling.  
The goal of this use case is to:

- Implement subtraction between two quantities
- Implement division operations on quantities
- Ensure unit consistency during arithmetic operations
- Maintain strict type safety across categories

Example:
- 5 ft - 2 ft
- 2 kg - 500 g
- 3 L - 500 mL
- 10 ft ÷ 2
- 4 kg ÷ 2

### Implementation
- Added `subtract()` method in the `Quantity` class
- Converted both operands to base unit before performing subtraction
- Implemented `divide()` method to support division by scalar values
- Ensured arithmetic operations are restricted within the same measurement category
- Preserved immutability by returning new Quantity objects
- Added comprehensive test cases for subtraction and division scenarios

### Concepts Used
- Arithmetic Operations on Domain Objects
- Base Unit Strategy
- Polymorphism
- Type Safety
- Immutability
- Clean Architecture
- Defensive Programming
- Unit Testing

### Outcome
Successfully implemented subtraction and division operations across multiple measurement categories.  
The system now supports full arithmetic capabilities while maintaining scalable and type-safe architecture.

🔗 [feature/UC12-QuantitySubtractionDivision](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC12-QuantitySubtractionDivision/src)

---

## UC13 - Centralized Arithmetic Logic to Enforce DRY in Quantity Operations

### Objective
To refactor arithmetic operations (addition, subtraction, division) by centralizing the logic in order to strictly enforce the DRY (Don't Repeat Yourself) principle.

### Problem Statement
With multiple arithmetic operations implemented (add, subtract, divide), there was duplication in:

- Base unit conversion logic
- Category validation logic
- Arithmetic execution steps
- Result reconstruction logic

The goal of this use case is to:

- Eliminate repetitive arithmetic logic
- Create a centralized internal operation handler
- Improve maintainability and scalability
- Ensure consistent behavior across all arithmetic methods

### Implementation
- Introduced a centralized private method to handle arithmetic operations
- Abstracted common steps:
  - Category validation
  - Conversion to base unit
  - Execution of arithmetic operation
  - Conversion to target unit
- Refactored existing methods (`add()`, `subtract()`, `divide()`) to delegate to centralized logic
- Reduced code duplication significantly
- Ensured no change in external behavior
- Updated test cases to confirm consistent functionality

### Concepts Used
- DRY Principle
- Refactoring
- Template Method Pattern (Conceptually)
- Clean Architecture
- Code Maintainability
- Separation of Concerns
- Defensive Programming
- Unit Testing

### Outcome
Successfully centralized arithmetic logic, reducing duplication and improving code clarity.  
The Quantity system is now more maintainable, scalable, and aligned with solid software design principles.

🔗 [feature/UC13-CentralizedArithmeticLogic](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC13-CentralizedArithmeticLogic/src)

---

## UC14 - Temperature Measurement with Selective Arithmetic Support and IMeasurable Refactoring

### Objective
To introduce Temperature as a new measurement category while refactoring the architecture using an `IMeasurable` interface and supporting selective arithmetic operations.

### Problem Statement
Temperature differs from other measurement types (Length, Weight, Volume) because:

- It requires offset-based conversion (e.g., Celsius ↔ Fahrenheit)
- Not all arithmetic operations are logically valid
- Addition of absolute temperatures is conceptually incorrect in many cases

The goal of this use case is to:

- Introduce Temperature units (Celsius, Fahrenheit, Kelvin)
- Support equality comparison and conversion
- Allow only valid arithmetic operations
- Refactor system using `IMeasurable` abstraction for better flexibility

### Implementation
- Created a `TemperatureUnit` enum implementing the `Unit` interface
- Implemented offset-based conversion logic (e.g., Celsius ↔ Fahrenheit)
- Introduced `IMeasurable` interface to define measurable behavior
- Ensured only meaningful arithmetic operations are permitted
- Restricted invalid operations through validation
- Updated the `Quantity` class to support selective arithmetic handling
- Added comprehensive unit tests for temperature equality and conversion

### Concepts Used
- Interface Segregation Principle (ISP)
- Polymorphism
- Offset-Based Conversion Logic
- Selective Arithmetic Enforcement
- Type Safety
- Clean Architecture Refactoring
- Defensive Programming
- Unit Testing

### Outcome
Successfully introduced Temperature measurement with proper conversion handling and selective arithmetic support.  
Refactored the system using `IMeasurable` abstraction, making the architecture more flexible, extensible, and aligned with SOLID design principles.

🔗 [feature/UC14-temperaturemeasurement](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC14-temperaturemeasurement/src)
