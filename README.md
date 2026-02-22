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
