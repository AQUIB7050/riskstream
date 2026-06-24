package com.riskstream.ingestion.service;

import com.riskstream.common.RiskEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, RiskEvent> kafkaTemplate;
    private final String topicName;

    public KafkaEventPublisher(
            KafkaTemplate<String, RiskEvent> kafkaTemplate,
            @Value("${riskstream.kafka.events-topic}") String topicName
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @Override
    public void publish(RiskEvent event) {
        kafkaTemplate.send(topicName, event.userId(), event);
    }
}
