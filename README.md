### UC18: Spring Security – Google Authentication & JWT  

- **Description:**  
  Secured the Spring Boot application using Spring Security with Google OAuth2 authentication and JWT-based authorization for stateless API access.

- **Architecture:**  
  - Security Layer – Handles authentication & authorization  
  - OAuth2 (Google) – User login via Google account  
  - JWT – Token-based authentication  
  - Controller – Secured REST endpoints  
  - Service – Business logic with role checks  

- **Implementation:**  
  - Integrated Spring Security  
  - Implemented Google OAuth2 login  
  - Generated & validated JWT tokens  
  - Secured APIs using filters and configurations  
  - Enabled role-based access control (RBAC)  

- **Example:**  
  Login via Google → Receive JWT → Access secured APIs with token

[UC18 - Spring Security](https://github.com/sanya-modi/QuantityMeasurementApp/tree/feature/UC18-GoogleAuthnetication/src)
