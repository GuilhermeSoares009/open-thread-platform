COMPOSE_FILE=infra/docker/docker-compose.yml
INFRA_SERVICES=postgres redis jaeger otel-collector

.PHONY: infra-up infra-down infra-logs infra-ps infra-config stack-up stack-down stack-logs

infra-up:
	docker compose -f $(COMPOSE_FILE) up -d $(INFRA_SERVICES)

infra-down:
	docker compose -f $(COMPOSE_FILE) stop $(INFRA_SERVICES)

infra-logs:
	docker compose -f $(COMPOSE_FILE) logs -f $(INFRA_SERVICES)

infra-ps:
	docker compose -f $(COMPOSE_FILE) ps

infra-config:
	docker compose -f $(COMPOSE_FILE) config

stack-up:
	docker compose -f $(COMPOSE_FILE) up -d

stack-down:
	docker compose -f $(COMPOSE_FILE) down

stack-logs:
	docker compose -f $(COMPOSE_FILE) logs -f
