# OpenThread Platform

Rebuild do OpenThread como MVP sem BFF, com backend em Java e worker em Go.

## Arquitetura atual
- `apps/api-java`: API Java 21 com Spring Boot (`/api/v1`)
- `apps/web`: frontend Next.js consumindo a API diretamente
- `apps/worker-go`: worker Go para eventos assincronos
- `infra`: Docker Compose com Postgres, Redis e observabilidade

## Execucao rapida
### Opcao A - Stack completa via Docker
1. `make stack-up`
2. Sem `make`: `docker compose -f infra/docker/docker-compose.yml up -d --build`

### Opcao B - Hibrido (apps locais + infra Docker)
1. `make infra-up`
2. API: `cd apps/api-java && mvn spring-boot:run`
3. Web: `cd apps/web && pnpm install && pnpm dev`
4. Worker: `cd apps/worker-go && go run ./cmd/worker`

## Endpoints principais
- Health: `GET /api/v1/health`
- OpenAPI: `GET /api/v1/openapi`
- Swagger UI: `GET /api/v1/swagger-ui`

## Encerrar stack
- `make stack-down`
- ou `docker compose -f infra/docker/docker-compose.yml down`

## Autenticacao para escrita (MVP)
As rotas de escrita usam `Authorization: Bearer <uuid>`.

Usuario de seed para testes:
- `11111111-1111-1111-1111-111111111111`
