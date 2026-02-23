## UC11 - Volume Measurement E
quality, Conversion, and Addition (Litre, Millilitre, Gallon)

### Objective
To extend the system to support volume measurements including equality comparison, unit conversion, and addition operations for Litre, Millilitre, and Gallon.

### Problem Statement
The system currently supports Length and Weight categories.  
The goal of this use case is to:

- Introduce Volume as a new measurement category
- Support units such as Litre (L), Millilitre (mL), and Gallon (gal)
- Enable equality comparison across volume units
- Allow conversion between supported volume units
- Implement addition of volume quantities
- Maintain strict type safety between categories (Volume ≠ Length ≠ Weight)

### Implementation
- Added a new VolumeUnit enum implementing the `Unit` interface
- Defined appropriate conversion factors using a base unit strategy (e.g., Litre as base)
- Implemented equality comparison across volume units
- Enabled unit-to-unit conversion for volume measurements
- Implemented addition of two volume quantities
- Ensured cross-category operations are restricted
- Added comprehensive test cases for equality, conversion, and addition

### Concepts Used
- Multi-Category Architecture
- Interface-Based Design
- Base Unit Strategy
- Polymorphism
- Type Safety
- Open-Closed Principle (OCP)
- Clean Code Practices
- Unit Testing

### Outcome
Successfully extended the system to support volume measurements including equality, conversion, and arithmetic operations.  
The application now functions as a scalable, multi-category measurement framework supporting Length, Weight, and Volume with consistent architecture.
