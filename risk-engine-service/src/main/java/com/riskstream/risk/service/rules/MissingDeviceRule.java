package com.riskstream.risk.service.rules;

import com.riskstream.common.EventType;
import com.riskstream.common.RiskEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MissingDeviceRule implements RiskRule {

    private static final Set<EventType> DEVICE_IMPORTANT_EVENTS = Set.of(
            EventType.LOGIN_SUCCESS,
            EventType.LOGIN_FAILURE,
            EventType.DEPOSIT,
            EventType.WITHDRAWAL
    );

    @Override
    public String code() {
        return "MISSING_DEVICE";
    }

    @Override
    public int score() {
        return 10;
    }

    @Override
    public boolean matches(RiskEvent event) {
        return DEVICE_IMPORTANT_EVENTS.contains(event.eventType())
                && (event.deviceId() == null || event.deviceId().isBlank());
    }
}
