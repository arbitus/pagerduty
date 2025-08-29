package com.example.pagerduty.pagerduty.service;

import com.example.pagerduty.client.PagerDutyClient;
import com.example.pagerduty.dto.ServiceResponseDto;
import com.example.pagerduty.dto.ServiceDto;
import com.example.pagerduty.dto.EscalationPolicyDto;
import com.example.pagerduty.dto.TeamDto;
import com.example.pagerduty.repository.EscalationPolicyRepository;
import com.example.pagerduty.repository.ServiceRepository;
import com.example.pagerduty.repository.TeamRepository;
import com.example.pagerduty.service.PagerDutySyncService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.Instant;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PagerDutySyncServiceTest {
    @Mock
    private PagerDutyClient client;
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private EscalationPolicyRepository escalationPolicyRepository;
    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private PagerDutySyncService syncService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSyncPagerDutyProcessesServices() {
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId("svc1");
        serviceDto.setName("Service 1");
        serviceDto.setSummary("Summary");
        serviceDto.setType("type");
        serviceDto.setSelf("self");
        serviceDto.setHtmlUrl("html");
        serviceDto.setStatus("active");
        serviceDto.setAutoResolveTimeout(10);
        serviceDto.setAcknowledgementTimeout(5);
        serviceDto.setAlertCreation("alert");
        serviceDto.setCreatedAt(Instant.now());

        EscalationPolicyDto policyDto = new EscalationPolicyDto();
        policyDto.setId("ep1");
        policyDto.setSummary("Policy");
        policyDto.setType("type");
        policyDto.setSelf("self");
        policyDto.setHtmlUrl("html");
        serviceDto.setEscalationPolicy(policyDto);

        TeamDto teamDto = new TeamDto();
        teamDto.setId("team1");
        teamDto.setSummary("Team");
        teamDto.setType("type");
        teamDto.setSelf("self");
        teamDto.setHtmlUrl("html");
        serviceDto.setTeams(Collections.singletonList(teamDto));

        ServiceResponseDto responseDto = new ServiceResponseDto();
        responseDto.setServices(Collections.singletonList(serviceDto));
        responseDto.setLimit(100);
        responseDto.setOffset(0);
        responseDto.setMore(false);
        responseDto.setTotal(1);

        when(client.getServices(anyInt(), anyInt())).thenReturn(responseDto);
        when(escalationPolicyRepository.findById(anyString())).thenReturn(Optional.empty());
        when(teamRepository.findById(anyString())).thenReturn(Optional.empty());
        when(serviceRepository.findById(anyString())).thenReturn(Optional.empty());

        Map<String, Object> result = syncService.syncPagerDuty();
        assertEquals("success", result.get("status"));
        assertEquals(1, result.get("services_processed"));
        assertEquals(1, result.get("escalation_policies_created"));
        assertEquals(1, result.get("teams_created"));
    }
}
