package com.riskstream.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public record RiskEvent(
        @NotBlank String eventId,
        @NotBlank String userId,
        @NotNull EventType eventType,
        @NotNull Instant occurredAt,
        String ipAddress,
        String deviceId,
        @PositiveOrZero BigDecimal amount,
        Map<String, String> attributes
) {
}
