# Infra

Recursos de infraestrutura local para desenvolvimento do rebuild.

- `docker/`: compose e arquivos relacionados aos containers locais.
- `observability/`: configuracoes de telemetria e tracing.

## Servicos locais
- PostgreSQL 16
- Redis 7
- Jaeger UI
- OpenTelemetry Collector

## Comandos na raiz
- `make infra-up`
- `make infra-down`
- `make infra-logs`
- `make infra-ps`

## Stack completa com Docker
- `make stack-up` sobe API Java, Web, Worker e infraestrutura
- `make stack-down` derruba toda a stack
- `make stack-logs` acompanha logs de todos os servicos

Sem `make`, use diretamente:
- `docker compose -f infra/docker/docker-compose.yml up -d`
- `docker compose -f infra/docker/docker-compose.yml down`
