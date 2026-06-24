package com.riskstream.ingestion.service;

import com.riskstream.common.RiskEvent;
import com.riskstream.ingestion.api.IngestEventRequest;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Component
public class EventMapper {

    private final Clock clock;

    public EventMapper(Clock clock) {
        this.clock = clock;
    }

    public RiskEvent toRiskEvent(IngestEventRequest request) {
        Instant occurredAt = request.occurredAt() == null ? clock.instant() : request.occurredAt();
        Map<String, String> attributes = request.attributes() == null ? Map.of() : Map.copyOf(request.attributes());

        return new RiskEvent(
                UUID.randomUUID().toString(),
                request.userId(),
                request.eventType(),
                occurredAt,
                request.ipAddress(),
                request.deviceId(),
                request.amount(),
                attributes
        );
    }
}
