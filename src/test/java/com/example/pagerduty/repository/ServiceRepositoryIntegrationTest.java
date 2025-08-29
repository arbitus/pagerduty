package com.example.pagerduty.repository;

import com.example.pagerduty.model.EscalationPolicyEntity;
import com.example.pagerduty.model.ServiceEntity;
import com.example.pagerduty.model.TeamEntity;
import com.example.pagerduty.repository.EscalationPolicyRepository;
import com.example.pagerduty.repository.ServiceRepository;
import com.example.pagerduty.repository.TeamRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ServiceRepositoryIntegrationTest {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private EscalationPolicyRepository escalationPolicyRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Test
    void testPersistServiceWithRelations() {
        EscalationPolicyEntity policy = new EscalationPolicyEntity();
        policy.setId("ep1");
        policy.setSummary("Policy");
        policy.setType("type");
        policy.setSelfUrl("self");
        policy.setHtmlUrl("html");
        escalationPolicyRepository.save(policy);

        TeamEntity team = new TeamEntity();
        team.setId("team1");
        team.setSummary("Team");
        team.setType("type");
        team.setSelfUrl("self");
        team.setHtmlUrl("html");
        teamRepository.save(team);

        ServiceEntity service = new ServiceEntity();
        service.setId("svc1");
        service.setName("Service 1");
        service.setSummary("Summary");
        service.setType("type");
        service.setSelfUrl("self");
        service.setHtmlUrl("html");
        service.setStatus("active");
        service.setAutoResolveTimeout(10);
        service.setAcknowledgementTimeout(5);
        service.setAlertCreation("alert");
        service.setCreatedAt(Instant.now());
        service.setEscalationPolicy(policy);
        Set<TeamEntity> teams = new HashSet<>();
        teams.add(team);
        service.setTeams(teams);
        serviceRepository.save(service);

        ServiceEntity found = serviceRepository.findById("svc1").orElse(null);
        assertNotNull(found);
        assertEquals("Service 1", found.getName());
        assertEquals("ep1", found.getEscalationPolicy().getId());
        assertEquals(1, found.getTeams().size());
        assertEquals("team1", found.getTeams().iterator().next().getId());
    }
}
