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
