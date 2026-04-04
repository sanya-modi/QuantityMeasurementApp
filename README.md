## UC19 - Quantity Measurement Microservices Project

## Project Overview
This project was built as a full microservices-based backend for a quantity measurement application. Instead of keeping all logic in one monolith, we split responsibilities into focused services so each part can scale, evolve, and be maintained independently.

The system supports user authentication, unit-based quantity operations, API routing, service discovery, and centralized monitoring. All services are designed around Spring Boot and communicate through a discovery-driven architecture.

## What We Built
We implemented five core services, each with a clear role:

- `eureka-server`: Service registry where all runtime services register themselves.
- `api-gateway`: Single entry point for client traffic, routing requests to internal services.
- `user-service`: User identity and authentication service supporting local JWT login and Google OAuth2 login.
- `measurement-service`: Business service for quantity comparison, conversion, arithmetic, and operation history.
- `admin-server`: Monitoring dashboard using Spring Boot Admin for service health and operational visibility.

This separation keeps infrastructure concerns, authentication concerns, and domain logic isolated and easier to manage.

## Architecture and Service Responsibilities
### 1) Service Discovery (`eureka-server`)
- Acts as the central registry.
- Other services register their instances and resolve each other through service names rather than fixed host URLs.
- Enables more flexible deployment and easier scaling.

### 2) API Gateway (`api-gateway`)
- Provides one public API front door.
- Routes measurement requests to `measurement-service` and auth/OAuth routes to `user-service`.
- Applies CORS configuration for frontend integration.
- Includes request/response timing logs through a global logging filter.
- Exposes gateway and actuator endpoints to support observability.

### 3) Authentication and User Management (`user-service`)
- Supports email/password registration and login.
- Generates JWT tokens after successful authentication.
- Supports Google OAuth2 login and maps Google profile data to internal users.
- Handles hybrid account behavior, allowing OAuth users to enable local credentials later.
- Maintains user profile data and login timestamps.
- Provides authenticated user status endpoints.

### 4) Quantity Measurement Domain (`measurement-service`)
- Executes core measurement operations:
  - compare
  - convert
  - add
  - subtract
  - multiply
  - divide
- Supports multiple measurement categories via unit abstractions (length, volume, weight, temperature).
- Persists both successful operations and errored operations for audit/history use cases.
- Provides global history and user-specific history endpoints.
- Supports filtering by operation/type, counting successful operations, and deleting or clearing user history.

### 5) Operational Monitoring (`admin-server`)
- Uses Spring Boot Admin server capabilities.
- Discovers registered services and surfaces health/actuator information.
- Improves runtime visibility for troubleshooting and status checks.

## Security Model
Security is implemented with practical separation between identity and domain access:

- `user-service` is the token issuer.
- JWT includes user identity claims such as user ID, role, and provider.
- `measurement-service` validates JWT on incoming requests and extracts user ID from token claims.
- Public operations are intentionally open for basic measurement usage, while user-specific history endpoints require authentication.
- OAuth2 and JWT flows coexist so users can authenticate through either local credentials or Google.

## Data and Persistence Strategy
- Both `user-service` and `measurement-service` use JPA/Hibernate.
- Configuration is primarily aligned for PostgreSQL in runtime settings.
- Runtime dependencies include PostgreSQL, MySQL, and H2 connectors for flexible environment usage.
- Measurement operations are persisted with enough context to support auditing, analytics, and personalized history.

## Observability and Operational Readiness
Across services, actuator endpoints are enabled for health and runtime metadata. Combined with:

- Eureka registration visibility,
- gateway request logging,
- and admin dashboard monitoring,

the platform provides a strong baseline for debugging and production-style diagnostics.

## API and Documentation Support
Both domain services include OpenAPI/Swagger support, making endpoint exploration easier for frontend and QA workflows.

## Key Outcomes of This Microservices Implementation
- Clean separation of infrastructure, identity, and business logic.
- Discovery-based inter-service communication through Eureka.
- Centralized API routing through a gateway instead of direct client-to-service coupling.
- Real authentication layer with JWT and OAuth2 support.
- Persistent business history for measurement operations, including error tracking.
- Monitoring-ready ecosystem through actuator and admin tooling.

## Overall Result
This project delivers a complete, extensible microservices foundation for a quantity measurement platform. It demonstrates practical enterprise patterns: discovery, gateway routing, token-based security, OAuth2 integration, service-level persistence, and operational monitoring, all organized as independently deployable Spring Boot services.
