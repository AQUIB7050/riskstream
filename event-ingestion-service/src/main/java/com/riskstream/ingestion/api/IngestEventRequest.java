package com.riskstream.ingestion.api;

import com.riskstream.common.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public record IngestEventRequest(
        @NotBlank String userId,
        @NotNull EventType eventType,
        Instant occurredAt,
        String ipAddress,
        String deviceId,
        @PositiveOrZero BigDecimal amount,
        Map<String, String> attributes
) {
}
