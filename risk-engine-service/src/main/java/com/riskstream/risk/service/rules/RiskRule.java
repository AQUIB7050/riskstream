package com.riskstream.risk.service.rules;

import com.riskstream.common.RiskEvent;

public interface RiskRule {

    String code();

    int score();

    boolean matches(RiskEvent event);
}
