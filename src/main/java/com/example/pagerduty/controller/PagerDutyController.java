package com.example.pagerduty.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pagerduty.service.PagerDutySyncService;

@RestController
public class PagerDutyController {

    private final PagerDutySyncService syncService;

    public PagerDutyController(PagerDutySyncService syncService) {
        this.syncService = syncService;
    }

    @PostMapping("/sync/pagerduty")
    public ResponseEntity<?> sync() {
        Map<String, Object> response = syncService.syncPagerDuty();
        return ResponseEntity.ok(response);
    }
}