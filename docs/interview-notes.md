# Interview Notes

## One-Minute Explanation

RiskStream is an event-driven fraud and behavior analytics platform. The ingestion service accepts user activity and transaction events, normalizes them, and publishes them to Kafka. The risk engine consumes those events and applies explainable rules to calculate a risk score. Later services will store alerts, expose analytics APIs, and power a React dashboard.

## Why I Designed It This Way

I separated ingestion from risk evaluation because those services have different reasons to change. Ingestion changes when external payloads or API contracts change. Risk evaluation changes when fraud rules or scoring policies change.

Kafka sits between them so traffic spikes do not directly overload the risk engine. It also makes the system extensible: analytics, alerting, and notification consumers can be added without changing ingestion.

## Current Rules

- `HIGH_VALUE_WITHDRAWAL`: flags withdrawals greater than or equal to 50000.
- `MISSING_DEVICE`: flags sensitive events that arrive without a device id.

## Interview Talking Points

- I used shared event contracts to keep producer and consumer payloads consistent.
- I kept the first version rule-based because fraud systems need explainability and auditability.
- I used Kafka so events can be processed asynchronously and replayed.
- I added unit tests around mapping and risk scoring because those are core business behaviors.

## Planned Next Steps

1. Add PostgreSQL persistence for alerts and risk decisions.
2. Add Redis-backed velocity checks, such as failed logins per user in a 10-minute window.
3. Add an analytics service with optimized SQL queries and indexes.
4. Add JWT/RBAC security for admin and analyst users.
5. Add a React dashboard for live alerts and user risk profiles.
