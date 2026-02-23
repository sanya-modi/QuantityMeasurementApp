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
