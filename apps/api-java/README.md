# API Java

Backend principal do OpenThread MVP.

## Stack
- Java 21
- Spring Boot 3
- PostgreSQL + Flyway
- Redis
- OpenTelemetry + Actuator

## Endpoints base
- Base path: `/api/v1`
- Health: `GET /api/v1/health`
- OpenAPI: `GET /api/v1/openapi`
- Swagger UI: `GET /api/v1/swagger-ui`

## Auth para escrita no MVP
Os endpoints de escrita exigem header `Authorization: Bearer <uuid-do-usuario>`.

Usuario seed para testes:
- `11111111-1111-1111-1111-111111111111`

## Variaveis principais
- `DB_URL` (default `jdbc:postgresql://localhost:5432/openthread`)
- `DB_USER` (default `openthread`)
- `DB_PASSWORD` (default `openthread`)
- `REDIS_HOST` (default `localhost`)
- `REDIS_PORT` (default `6379`)
- `OTEL_EXPORTER_OTLP_ENDPOINT` (default `http://localhost:4318/v1/traces`)

## Rodar local
1. Subir infraestrutura na raiz do repo:
   - `make infra-up`
2. Rodar a API:
   - `mvn spring-boot:run`

## Rodar via Docker
- Na raiz do repo: `make stack-up`

## Testes
- `mvn test`
