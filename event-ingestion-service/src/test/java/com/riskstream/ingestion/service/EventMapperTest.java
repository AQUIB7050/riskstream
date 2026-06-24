package com.riskstream.ingestion.service;

import com.riskstream.common.EventType;
import com.riskstream.common.RiskEvent;
import com.riskstream.ingestion.api.IngestEventRequest;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class EventMapperTest {

    private final Clock fixedClock = Clock.fixed(Instant.parse("2026-06-24T10:15:30Z"), ZoneOffset.UTC);
    private final EventMapper eventMapper = new EventMapper(fixedClock);

    @Test
    void fillsServerTimeAndEmptyAttributesWhenOptionalFieldsAreMissing() {
        IngestEventRequest request = new IngestEventRequest(
                "user-101",
                EventType.LOGIN_FAILURE,
                null,
                "10.0.0.5",
                "device-a",
                null,
                null
        );

        RiskEvent event = eventMapper.toRiskEvent(request);

        assertThat(event.eventId()).isNotBlank();
        assertThat(event.userId()).isEqualTo("user-101");
        assertThat(event.eventType()).isEqualTo(EventType.LOGIN_FAILURE);
        assertThat(event.occurredAt()).isEqualTo(Instant.parse("2026-06-24T10:15:30Z"));
        assertThat(event.attributes()).isEmpty();
    }

    @Test
    void preservesClientOccurredAtWhenProvided() {
        Instant clientTime = Instant.parse("2026-06-24T09:00:00Z");
        IngestEventRequest request = new IngestEventRequest(
                "user-202",
                EventType.DEPOSIT,
                clientTime,
                null,
                null,
                null,
                Map.of("currency", "INR")
        );

        RiskEvent event = eventMapper.toRiskEvent(request);

        assertThat(event.occurredAt()).isEqualTo(clientTime);
        assertThat(event.attributes()).containsEntry("currency", "INR");
    }
}
