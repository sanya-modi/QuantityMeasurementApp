###  UC16: Database Persistence Layer Integration

* **Description:**
  UC16 extends the N-Tier architecture by replacing the cache-based repository with a **database-backed persistence layer**. The application now stores and retrieves quantity measurements using JDBC and a connection pool. This improves scalability and enables persistent storage while maintaining the same layered architecture introduced in UC15.

* **Architecture:**

  * **Controller** – Handles incoming requests and forwards them to the service layer.
  * **Service** – Performs business logic, conversions, and arithmetic operations.
  * **Repository** – Provides **database-based storage** using JDBC instead of in-memory caching.
  * **Connection Pool** – Manages reusable database connections for efficient access.
  * **DTO / Model / Entity** – Continue to support structured data transfer and internal representation.

* **Implementation:**

  * Introduced `QuantityMeasurementDatabaseRepository` to replace the cache repository.
  * Implemented database operations using **JDBC (`Connection`, `PreparedStatement`, `ResultSet`)**.
  * Added `ConnectionPool` utility for managing database connections.
  * Repository stores measurement results in the **`quantity_measurement` table**.
  * Service layer continues performing **DTO → Model → Quantity → Model → DTO** transformations.
  * Existing **Controller and Service logic remain unchanged**, ensuring backward compatibility.

* **Example:**

  * `QuantityDTO(5, FEET, LENGTH) + QuantityDTO(24, INCHES, LENGTH) → QuantityDTO(7, FEET, LENGTH)`
  * Result is **stored in the database** with a unique key.
  * `find(key)` retrieves the stored measurement entity from the database.
