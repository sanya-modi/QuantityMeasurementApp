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
