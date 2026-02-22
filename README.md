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
