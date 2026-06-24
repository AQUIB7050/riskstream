# RiskStream

RiskStream is a real-time fraud and user behavior analytics platform built with Java and Spring Boot.

The goal is to demonstrate production-style backend engineering: event-driven microservices, Kafka-based async processing, Redis-backed velocity checks, SQL analytics, clean architecture, and explainable risk scoring.

## Current Phase

Phase 1 builds the foundation:

- Multi-module Maven project with Maven Wrapper.
- Shared event contract in `common-events`.
- Event ingestion microservice on port `8081`.
- Risk engine microservice on port `8082`.
- Kafka, PostgreSQL, and Redis infrastructure through Docker Compose.
- Basic rule-based risk scoring with unit tests.

## High-Level Event Flow

```text
Client/System
    |
    | POST /api/events
    v
Event Ingestion Service
    |
    | publishes RiskEvent
    v
Kafka topic: riskstream.events.raw
    |
    | consumes event
    v
Risk Engine Service
    |
    | evaluates rules and score
    v
Alerts and analytics services in later phases
```

## Run Tests

```bash
./mvnw test
```

## Run Infrastructure

```bash
docker compose up -d
```

## Run Services

In separate terminals:

```bash
./mvnw -pl event-ingestion-service spring-boot:run
./mvnw -pl risk-engine-service spring-boot:run
```

## Sample Event

```bash
curl -X POST http://localhost:8081/api/events \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user-101",
    "eventType": "WITHDRAWAL",
    "ipAddress": "10.0.0.5",
    "amount": 75000,
    "attributes": {
      "currency": "INR"
    }
  }'
```

Expected result: the ingestion service returns `ACCEPTED`, Kafka receives the event, and the risk engine logs a risk score.
