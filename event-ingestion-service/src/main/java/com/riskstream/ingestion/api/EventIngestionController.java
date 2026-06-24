package com.riskstream.ingestion.api;

import com.riskstream.common.RiskEvent;
import com.riskstream.ingestion.service.EventMapper;
import com.riskstream.ingestion.service.EventPublisher;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventIngestionController {

    private final EventMapper eventMapper;
    private final EventPublisher eventPublisher;

    public EventIngestionController(EventMapper eventMapper, EventPublisher eventPublisher) {
        this.eventMapper = eventMapper;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public EventAcceptedResponse ingest(@Valid @RequestBody IngestEventRequest request) {
        RiskEvent event = eventMapper.toRiskEvent(request);
        eventPublisher.publish(event);
        return new EventAcceptedResponse(event.eventId(), "ACCEPTED");
    }
}
