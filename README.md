###  UC15: N-Tier Architecture Refactoring

- Description: UC15 restructures the Quantity Measurement App into a layered architecture by introducing Controller, Service, Repository, DTO, Model, and Entity layers. This separation improves maintainability, modularity, and testability while preserving all measurement logic implemented in previous use cases.

- Architecture:

  - **Controller** – Handles requests and delegates operations to the service layer.
  - **Service** – Contains business logic and coordinates conversions and operations.
  - **Repository** – Provides a cache-based storage layer.
  - **DTO / Model / Entity** – Used for structured data transfer and internal representation.

- Implementation:

  - Introduced `QuantityMeasurementController`, `QuantityMeasurementServiceImpl`, and `QuantityMeasurementCacheRepository`.
  - Added `QuantityDTO`, `QuantityModel`, and `QuantityMeasurementEntity`.
  - Service performs **DTO → Model → Quantity → Model → DTO** transformation.
  - Reuses the existing generic `Quantity` engine and unit enums from previous UCs.

- Example:

  - `QuantityDTO(10, FEET, LENGTH) + QuantityDTO(12, INCHES, LENGTH) → QuantityDTO(11, FEET, LENGTH)`
  - `QuantityDTO(100, CELSIUS, TEMPERATURE).equals(QuantityDTO(212, FAHRENHEIT, TEMPERATURE)) → true`
