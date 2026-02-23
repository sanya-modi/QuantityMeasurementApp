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
