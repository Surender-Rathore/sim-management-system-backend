# SIM Card Management System

A minimal Spring Boot 3 CRUD service for managing SIM cards, structured in clean layers
(Controller → Service → Repository) with DTOs, bean validation, and centralized exception handling.

## Stack
- Java 21, Spring Boot 3.3.5
- Spring Web, Spring Data JPA, Bean Validation
- H2 in-memory database (swap for Postgres/MySQL by changing the datasource + driver)

## Run
```bash
mvn spring-boot:run
```
App starts on `http://localhost:8080`.
H2 console: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:simdb`, user `sa`, empty password).

## Domain: SimCard
| Field          | Notes                                          |
|----------------|------------------------------------------------|
| iccid          | Unique. 19–22 digits.                          |
| imsi           | 14–15 digits.                                  |
| msisdn         | Unique phone number. 7–15 digits.              |
| carrier        | Required.                                      |
| planType       | Optional.                                      |
| status         | ACTIVE / INACTIVE / SUSPENDED / BLOCKED.       |
| assignedTo     | Optional owner.                                |
| activationDate | Optional (ISO date).                           |

## Endpoints
| Method | Path                     | Description        | Success |
|--------|--------------------------|--------------------|---------|
| POST   | /api/v1/sim-cards        | Create a SIM       | 201     |
| GET    | /api/v1/sim-cards        | List all SIMs      | 200     |
| GET    | /api/v1/sim-cards/{id}   | Get one SIM        | 200     |
| PUT    | /api/v1/sim-cards/{id}   | Update a SIM       | 200     |
| DELETE | /api/v1/sim-cards/{id}   | Delete a SIM       | 204     |

Errors return RFC-7807 `application/problem+json` (404 not found, 409 duplicate, 400 validation).

## API documentation (OpenAPI)
Generated live by springdoc from the code, and also shipped as a static spec.

| Resource | Location |
|----------|----------|
| Swagger UI | `http://localhost:8080/swagger-ui.html` |
| OpenAPI JSON | `http://localhost:8080/v3/api-docs` |
| OpenAPI YAML | `http://localhost:8080/v3/api-docs.yaml` |
| Static spec | [`docs/openapi.yaml`](docs/openapi.yaml) |

## Sample requests
```bash
# Create
curl -X POST http://localhost:8080/api/v1/sim-cards \
  -H "Content-Type: application/json" \
  -d '{
        "iccid": "8949012345678909999",
        "imsi": "262031234567892",
        "msisdn": "4915199999999",
        "carrier": "O2",
        "planType": "Postpaid-L",
        "status": "ACTIVE",
        "assignedTo": "New Customer",
        "activationDate": "2026-08-31"
      }'

# List all
curl http://localhost:8080/api/v1/sim-cards

# Get by id
curl http://localhost:8080/api/v1/sim-cards/1

# Update
curl -X PUT http://localhost:8080/api/v1/sim-cards/1 \
  -H "Content-Type: application/json" \
  -d '{ "iccid":"8949012345678901234","imsi":"262011234567890","msisdn":"4915112345678","carrier":"Telekom","planType":"Prepaid-S","status":"SUSPENDED","assignedTo":"Om Sarangani","activationDate":"2025-01-15" }'

# Delete
curl -X DELETE http://localhost:8080/api/v1/sim-cards/1
```

## Switching to a real database (example: PostgreSQL)
Add the driver to `pom.xml`, then in `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/simdb
    username: sim
    password: sim
  jpa:
    hibernate:
      ddl-auto: validate
```
Manage schema with Flyway/Liquibase instead of `ddl-auto` for production.