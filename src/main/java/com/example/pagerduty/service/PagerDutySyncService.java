package com.example.pagerduty.service;

import com.example.pagerduty.client.PagerDutyClient;
import com.example.pagerduty.dto.ServiceResponseDto;
import com.example.pagerduty.dto.ServiceDto;
import com.example.pagerduty.dto.TeamDto;
import com.example.pagerduty.dto.EscalationPolicyDto;
import com.example.pagerduty.model.EscalationPolicyEntity;
import com.example.pagerduty.model.ServiceEntity;
import com.example.pagerduty.model.TeamEntity;
import com.example.pagerduty.repository.EscalationPolicyRepository;
import com.example.pagerduty.repository.ServiceRepository;
import com.example.pagerduty.repository.TeamRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PagerDutySyncService {

    private final PagerDutyClient client;
    private final ServiceRepository serviceRepository;
    private final EscalationPolicyRepository escalationPolicyRepository;
    private final TeamRepository teamRepository;

    public PagerDutySyncService(PagerDutyClient client,
            ServiceRepository serviceRepository,
            EscalationPolicyRepository escalationPolicyRepository,
            TeamRepository teamRepository) {
        this.client = client;
        this.serviceRepository = serviceRepository;
        this.escalationPolicyRepository = escalationPolicyRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public Map<String, Object> syncPagerDuty() {
        int limit = 100;
        int offset = 0;
        int processed = 0;
        int policiesCreated = 0, policiesUpdated = 0;
        int teamsCreated = 0, teamsUpdated = 0;

        boolean more = true;

        while (more) {
            ServiceResponseDto response = client.getServices(limit, offset);
            for (ServiceDto dto : response.getServices()) {

                // Service Upsert
                ServiceEntity service = serviceRepository.findById(dto.getId())
                        .orElseGet(ServiceEntity::new);

                service.setId(dto.getId());
                service.setName(dto.getName());
                service.setSummary(dto.getSummary());
                service.setType(dto.getType());
                service.setSelfUrl(dto.getSelf());
                service.setHtmlUrl(dto.getHtmlUrl());
                service.setStatus(dto.getStatus());
                service.setAutoResolveTimeout(dto.getAutoResolveTimeout());
                service.setAcknowledgementTimeout(dto.getAcknowledgementTimeout());
                service.setAlertCreation(dto.getAlertCreation());
                service.setCreatedAt(dto.getCreatedAt());

                // Escalation Policy Upsert
                EscalationPolicyDto escalationPolicyDto = dto.getEscalationPolicy();
                if (escalationPolicyDto == null) {
                    System.out.println("[SYNC] Service " + dto.getId() + " has no escalation policy DTO");
                } else if (escalationPolicyDto.getId() == null || escalationPolicyDto.getId().isEmpty()) {
                    System.out.println(
                            "[SYNC] Service " + dto.getId() + " has escalation policy DTO but ID is null or empty");
                }
                if (escalationPolicyDto != null && escalationPolicyDto.getId() != null
                        && !escalationPolicyDto.getId().isEmpty()) {
                    EscalationPolicyEntity policy = escalationPolicyRepository
                            .findById(escalationPolicyDto.getId())
                            .orElseGet(EscalationPolicyEntity::new);

                    boolean newPolicy = (policy.getId() == null);
                    policy.setId(escalationPolicyDto.getId());
                    policy.setSummary(escalationPolicyDto.getSummary());
                    policy.setType(escalationPolicyDto.getType());
                    policy.setSelfUrl(escalationPolicyDto.getSelf());
                    policy.setHtmlUrl(escalationPolicyDto.getHtmlUrl());
                    escalationPolicyRepository.save(policy);

                    if (newPolicy)
                        policiesCreated++;
                    else
                        policiesUpdated++;
                    service.setEscalationPolicy(policy);
                } else {
                    service.setEscalationPolicy(null);
                    // Opcional: log.warn("Service " + dto.getId() + " has no escalation policy");
                }

                // Limpiar teams anteriores y re-asignar
                service.getTeams().clear();

                if (dto.getTeams() != null) {
                    for (TeamDto t : dto.getTeams()) {
                        TeamEntity team = teamRepository.findById(t.getId())
                                .orElseGet(TeamEntity::new);

                        boolean newTeam = (team.getId() == null);
                        team.setId(t.getId());
                        team.setSummary(t.getSummary());
                        team.setType(t.getType());
                        team.setSelfUrl(t.getSelf());
                        team.setHtmlUrl(t.getHtmlUrl());
                        team.setName(t.getName());
                        team.setDescription(t.getDescription());
                        teamRepository.save(team);
                        service.getTeams().add(team);

                        if (newTeam)
                            teamsCreated++;
                        else
                            teamsUpdated++;
                    }
                }

                serviceRepository.save(service);
                processed++;
            }

            more = response.isMore();
            offset += limit;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("message", "Synchronization completed successfully");
        result.put("services_processed", processed);
        result.put("escalation_policies_created", policiesCreated);
        result.put("escalation_policies_updated", policiesUpdated);
        result.put("teams_created", teamsCreated);
        result.put("teams_updated", teamsUpdated);

        return result;
    }
}
