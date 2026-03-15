## UC16 - Database Integration with JDBC for Quantity Measurement Persistence

### Objective
To introduce persistent database storage into the Quantity Measurement Application using JDBC.  
This use case replaces the in-memory repository with a database-backed repository, enabling long-term data persistence, historical tracking, and scalable storage.

### Problem Statement
The previous implementation (UC1–UC15) stored measurement data in memory using a cache repository. While this worked for basic functionality, it had several limitations:

- Data was lost on application restart
- No support for concurrent access
- Limited querying capabilities
- No structured schema validation
- Difficult to perform analytics or reporting
- Poor scalability for large datasets

The goal of this use case is to integrate a relational database using JDBC while preserving the existing N-Tier architecture. :contentReference[oaicite:0]{index=0}

### Implementation
- Introduced a new repository implementation:
  - `QuantityMeasurementDatabaseRepository`

- Integrated JDBC for database interaction:
  - Connection management
  - SQL query execution
  - Result mapping to entity objects

- Implemented a **connection pool** to efficiently manage database connections.

- Added configuration management:
  - `application.properties` for database configuration
  - `ApplicationConfig` class to load environment properties

- Added database utilities:
  - `ConnectionPool` for managing reusable connections

- Created database schema:
  - `quantity_measurement_entity` table
  - `quantity_measurement_history` table for audit tracking

- Implemented **parameterized SQL queries** to prevent SQL injection.

- Extended exception hierarchy:
  - Added `DatabaseException` for database-related errors.

- Updated the service layer to support **both repositories**:
  - Cache repository
  - Database repository

- Used **Dependency Injection** to dynamically switch repository implementations.

- Added **Maven project structure**:
  - `src/main/java`
  - `src/main/resources`
  - `src/test/java`

- Added Maven configuration (`pom.xml`) with dependencies for:
  - JDBC drivers
  - H2 database (for testing)
  - JUnit testing
  - SLF4J logging
  - Maven build plugins

### Concepts Used
- JDBC (Java Database Connectivity)
- Connection Pooling
- Maven Build System
- Parameterized SQL Queries
- Database Schema Design
- Transaction Management
- Repository Pattern
- Dependency Injection
- Exception Hierarchy
- Logging Framework (SLF4J)
- Integration Testing with H2 Database

### Outcome
Successfully integrated database persistence into the Quantity Measurement Application.  
Measurement operations are now stored in a relational database, enabling data durability, querying capabilities, and scalable storage while maintaining compatibility with the existing N-Tier architecture. :contentReference[oaicite:1]{index=1}
