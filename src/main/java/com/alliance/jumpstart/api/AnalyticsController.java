package com.alliance.jumpstart.api;

import org.springframework.web.bind.annotation.RestController;

import com.alliance.jumpstart.responses.AnalyticsDataResponse;
import com.alliance.jumpstart.services.AnalyticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AnalyticsController {

    private AnalyticsService service;

    @Autowired
    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @GetMapping(value = "/api/analytics")
    public AnalyticsDataResponse getCurrentStatistics() {
        return service.createAnalyticsResponse();
    }
}