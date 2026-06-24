package com.riskstream.risk.messaging;

import com.riskstream.common.RiskEvent;
import com.riskstream.risk.service.RiskDecision;
import com.riskstream.risk.service.RiskEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RiskEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(RiskEventConsumer.class);

    private final RiskEngine riskEngine;

    public RiskEventConsumer(RiskEngine riskEngine) {
        this.riskEngine = riskEngine;
    }

    @KafkaListener(topics = "${riskstream.kafka.events-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(RiskEvent event) {
        RiskDecision decision = riskEngine.evaluate(event);
        log.info(
                "risk_decision eventId={} userId={} score={} triggeredRules={}",
                event.eventId(),
                event.userId(),
                decision.score(),
                decision.triggeredRules()
        );
    }
}
