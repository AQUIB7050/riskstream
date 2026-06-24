package com.riskstream.risk.service.rules;

import com.riskstream.common.EventType;
import com.riskstream.common.RiskEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class HighValueWithdrawalRule implements RiskRule {

    private static final BigDecimal HIGH_VALUE_WITHDRAWAL_LIMIT = new BigDecimal("50000");

    @Override
    public String code() {
        return "HIGH_VALUE_WITHDRAWAL";
    }

    @Override
    public int score() {
        return 40;
    }

    @Override
    public boolean matches(RiskEvent event) {
        return event.eventType() == EventType.WITHDRAWAL
                && event.amount() != null
                && event.amount().compareTo(HIGH_VALUE_WITHDRAWAL_LIMIT) >= 0;
    }
}
