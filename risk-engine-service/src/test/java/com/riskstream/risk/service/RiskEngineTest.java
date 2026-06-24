package com.riskstream.risk.service;

import com.riskstream.common.EventType;
import com.riskstream.common.RiskEvent;
import com.riskstream.risk.service.rules.HighValueWithdrawalRule;
import com.riskstream.risk.service.rules.MissingDeviceRule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RiskEngineTest {

    private final RiskEngine riskEngine = new RiskEngine(List.of(
            new HighValueWithdrawalRule(),
            new MissingDeviceRule()
    ));

    @Test
    void addsScoresForAllMatchingRules() {
        RiskEvent event = new RiskEvent(
                "evt-1",
                "user-1",
                EventType.WITHDRAWAL,
                Instant.parse("2026-06-24T10:15:30Z"),
                "10.0.0.1",
                null,
                new BigDecimal("75000"),
                Map.of()
        );

        RiskDecision decision = riskEngine.evaluate(event);

        assertThat(decision.score()).isEqualTo(50);
        assertThat(decision.triggeredRules())
                .containsExactlyInAnyOrder("HIGH_VALUE_WITHDRAWAL", "MISSING_DEVICE");
    }

    @Test
    void returnsZeroScoreWhenNoRuleMatches() {
        RiskEvent event = new RiskEvent(
                "evt-2",
                "user-2",
                EventType.DEPOSIT,
                Instant.parse("2026-06-24T10:15:30Z"),
                "10.0.0.2",
                "device-2",
                new BigDecimal("1000"),
                Map.of()
        );

        RiskDecision decision = riskEngine.evaluate(event);

        assertThat(decision.score()).isZero();
        assertThat(decision.triggeredRules()).isEmpty();
    }
}
