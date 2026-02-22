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
