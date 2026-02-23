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
