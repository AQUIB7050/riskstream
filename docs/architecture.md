# RiskStream Architecture

## System Goal

RiskStream detects suspicious behavior from user and transaction events in near real time.

The system is intentionally built around explainable rules instead of heavy machine learning. This matters because fraud and risk systems often need auditability: an analyst should be able to answer why a user was flagged.

## Microservices

### Event Ingestion Service

Responsibility:

- Accept raw events from clients or internal systems.
- Validate required fields.
- Normalize the event into a shared `RiskEvent`.
- Publish the event to Kafka.

Why separate it:

- It protects downstream services from unstable external payloads.
- It can scale horizontally when event traffic increases.
- It keeps API concerns separate from risk logic.

### Risk Engine Service

Responsibility:

- Consume normalized events from Kafka.
- Evaluate deterministic risk rules.
- Produce a risk score and triggered rule list.

Why separate it:

- Risk rules change more often than ingestion APIs.
- It can scale independently based on Kafka lag.
- It keeps fraud decisions explainable and testable.

### Future Services

- Alert Service: stores alerts, investigation status, and audit notes.
- Analytics Service: exposes SQL-backed dashboards and reports.
- User Service: handles users, roles, JWT auth, and account/device profiles.

## Why Kafka

Kafka gives the project an event-driven backbone:

- Producers and consumers are decoupled.
- Risk processing can happen asynchronously.
- Events can be replayed when rules change.
- Multiple consumers can read the same event stream later, such as analytics and notifications.

## Why Minimal ML

The project uses a heuristic risk score:

```text
final score = sum of scores from triggered rules
```

This is not pretending to be a trained ML model. It is deliberately explainable:

- `HIGH_VALUE_WITHDRAWAL` means the withdrawal amount crossed the configured threshold.
- `MISSING_DEVICE` means a sensitive event arrived without device information.

This gives you a strong interview answer: start with rules for auditability, then optionally add ML later after enough labeled historical data exists.
