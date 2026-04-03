### UC17: Spring Framework Integration - REST Services & JPA  

- **Description:**  
  Migrated the application to a Spring Boot REST service with embedded server, replacing JDBC with Spring Data JPA for ORM-based persistence.

- **Architecture:**  
  - Controller – Handles REST API requests  
  - Service – Business logic & transactions  
  - Repository – JPA-based data access  
  - Database – ORM using Hibernate  
  - Spring Boot – Auto-config + embedded Tomcat  

- **Implementation:**  
  - Built REST APIs using `@RestController`  
  - Replaced JDBC with Spring Data JPA  
  - Used DI (`@Autowired`), `@Transactional`  
  - Added exception handling & validation  
  - Integrated Swagger, Actuator, and testing (MockMvc)  
  - Optional Spring Security integration  

- **Example:**  
  `POST /quantity/add` → Returns calculated result stored via JPA

  [UC17 - Spring Backend](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC17-SpringBackend/src)
