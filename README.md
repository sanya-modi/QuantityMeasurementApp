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
