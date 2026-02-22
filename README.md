## UC7 - Addition with Target Unit Specification

### Objective
To enhance the addition functionality by allowing the result to be returned in a specified target unit.

### Problem Statement
The system currently supports addition of two length quantities.  
The goal of this use case is to:

- Add two quantities with different units
- Allow the user to specify the desired output unit
- Return the final result in the requested unit

Example:
- 1 ft + 12 in → result in ft
- 2 yd + 1 ft → result in in
- 5 cm + 2 in → result in cm

### Implementation
- Extended the `add()` method to accept a target unit parameter
- Converted both input quantities to a base unit
- Performed addition in base unit
- Converted the final result to the specified target unit
- Added test cases for different target unit scenarios

### Concepts Used
- Method Overloading / Parameterized Methods
- Base Unit Strategy
- Flexible API Design
- Clean Architecture
- Reusability
- Unit Testing

### Outcome
Successfully implemented addition with target unit specification.  
The system now provides flexible and user-controlled arithmetic operations across different measurement units.
