package com.riskstream.ingestion.service;

import com.riskstream.common.RiskEvent;

public interface EventPublisher {

    void publish(RiskEvent event);
}
