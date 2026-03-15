# Quantity Measurement App

## UC15 - N-Tier Architecture Refactoring for Quantity Measurement Application

### Objective
To refactor the Quantity Measurement Application into a professional N-Tier architecture by separating responsibilities into distinct layers such as Controller, Service, Repository, and Model.

### Problem Statement
The previous implementation (UC1–UC14) followed a largely monolithic structure where multiple responsibilities were handled within a single application flow.

The goal of this use case is to:

- Separate concerns across multiple architectural layers
- Improve maintainability, scalability, and testability
- Introduce clean architecture principles
- Prepare the system for future extensions such as REST APIs, persistence layers, and dependency injection frameworks

### Implementation
- Refactored the application into a **4-Tier Architecture**:
  - **Application Layer** – Entry point (`QuantityMeasurementApp`) that initializes components
  - **Controller Layer** – Handles user interaction and delegates operations
  - **Service Layer** – Contains business logic for comparison, conversion, and arithmetic operations
  - **Repository Layer** – Manages storage of quantity measurement data
  - **Entity / Model Layer** – Defines DTOs, internal models, and entities

- Introduced supporting classes:
  - `QuantityMeasurementController`
  - `QuantityMeasurementServiceImpl`
  - `QuantityMeasurementCacheRepository`
  - `QuantityDTO`, `QuantityModel`, `QuantityMeasurementEntity`

- Implemented design patterns:
  - **Factory Pattern** – Object creation for controllers and services
  - **Facade Pattern** – Controller provides simplified interface to system operations
  - **Singleton Pattern** – Repository instance management
  - **Dependency Injection** – Decoupling components
  - **Interface Segregation Principle (ISP)** for service and repository interfaces

### Concepts Used
- N-Tier Architecture
- Separation of Concerns
- SOLID Principles
- Factory Design Pattern
- Facade Design Pattern
- Singleton Pattern
- Dependency Injection
- DTO and POJO Design
- Layered Architecture

### Outcome
Successfully transformed the application from a monolithic design into a scalable N-Tier architecture.  
The system is now more modular, maintainable, and easier to test, while maintaining backward compatibility with all previous use cases (UC1–UC14).
