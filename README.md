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
