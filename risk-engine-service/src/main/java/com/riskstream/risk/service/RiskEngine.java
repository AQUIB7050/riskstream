package com.riskstream.risk.service;

import com.riskstream.common.RiskEvent;
import com.riskstream.risk.service.rules.RiskRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskEngine {

    private final List<RiskRule> rules;

    public RiskEngine(List<RiskRule> rules) {
        this.rules = rules;
    }

    public RiskDecision evaluate(RiskEvent event) {
        List<RiskRule> matchedRules = rules.stream()
                .filter(rule -> rule.matches(event))
                .toList();

        List<String> triggeredRules = matchedRules.stream()
                .map(RiskRule::code)
                .toList();

        int score = matchedRules.stream()
                .mapToInt(RiskRule::score)
                .sum();

        return new RiskDecision(score, triggeredRules);
    }
}
