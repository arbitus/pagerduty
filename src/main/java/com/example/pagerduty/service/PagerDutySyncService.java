package com.example.pagerduty.service;

import com.example.pagerduty.client.PagerDutyClient;
import com.example.pagerduty.dto.ServiceResponseDto;
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
            for (ServiceResponseDto.ServiceDto dto : response.getServices()) {

                // Escalation Policy Upsert
                EscalationPolicyEntity policy = escalationPolicyRepository
                        .findById(dto.getEscalation_policy().getId())
                        .orElseGet(EscalationPolicyEntity::new);

                boolean newPolicy = (policy.getId() == null);
                policy.setId(dto.getEscalation_policy().getId());
                policy.setSummary(dto.getEscalation_policy().getSummary());
                policy.setType(dto.getEscalation_policy().getType());
                policy.setSelfUrl(dto.getEscalation_policy().getSelf());
                policy.setHtmlUrl(dto.getEscalation_policy().getHtml_url());
                escalationPolicyRepository.save(policy);

                if (newPolicy)
                    policiesCreated++;
                else
                    policiesUpdated++;

                // Service Upsert
                ServiceEntity service = serviceRepository.findById(dto.getId())
                        .orElseGet(ServiceEntity::new);

                service.setId(dto.getId());
                service.setName(dto.getName());
                service.setSummary(dto.getSummary());
                service.setType(dto.getType());
                service.setSelfUrl(dto.getSelf());
                service.setHtmlUrl(dto.getHtml_url());
                service.setStatus(dto.getStatus());
                service.setAutoResolveTimeout(dto.getAuto_resolve_timeout());
                service.setAcknowledgementTimeout(dto.getAcknowledgement_timeout());
                service.setAlertCreation(dto.getAlert_creation());
                service.setCreatedAt(dto.getCreated_at());
                service.setEscalationPolicy(policy);

                // Limpiar teams anteriores y re-asignar
                service.getTeams().clear();

                if (dto.getTeams() != null) {
                    for (ServiceResponseDto.TeamDto t : dto.getTeams()) {
                        TeamEntity team = teamRepository.findById(t.getId())
                                .orElseGet(TeamEntity::new);

                        boolean newTeam = (team.getId() == null);
                        team.setId(t.getId());
                        team.setSummary(t.getSummary());
                        team.setType(t.getType());
                        team.setSelfUrl(t.getSelf());
                        team.setHtmlUrl(t.getHtml_url());
                        team.setName(t.getName()); // Mapping name from TeamDto to TeamEntity
                        team.setDescription(t.getDescription()); // Mapping description from TeamDto to TeamEntity
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
