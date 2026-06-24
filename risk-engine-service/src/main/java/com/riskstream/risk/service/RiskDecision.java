package com.riskstream.risk.service;

import java.util.List;

public record RiskDecision(int score, List<String> triggeredRules) {
}
