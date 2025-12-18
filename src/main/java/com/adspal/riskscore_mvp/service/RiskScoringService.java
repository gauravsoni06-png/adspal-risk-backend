package com.adspal.riskscore_mvp.service;


import com.adspal.riskscore_mvp.entity.Brand;
import com.adspal.riskscore_mvp.entity.RiskEvent;
import org.springframework.stereotype.Service;

@Service
public class RiskScoringService {

    public RiskEvent calculateRisk(Brand brand, RiskEvent event) {

        int score = 0;

        // Payment type
        if ("COD".equalsIgnoreCase(event.getPaymentType())) score += 30;

        // High order value
        if (event.getOrderValue() > 2000) score += 20;

        // Past RTO history
        score += event.getPastRto() * 15;

        // Low order history
        if (event.getPastOrders() < 3) score += 15;

        // Cap score
        if (score > 100) score = 100;

        event.setRiskScore(score);

        if (score >= 70) {
            event.setRiskLevel("HIGH");
            event.setRecommendation("Block COD / Force Prepaid");
        } else if (score >= 40) {
            event.setRiskLevel("MEDIUM");
            event.setRecommendation("Allow COD with OTP");
        } else {
            event.setRiskLevel("LOW");
            event.setRecommendation("Allow COD");
        }
        event.setBrand(brand);
        return event;
    }

}
