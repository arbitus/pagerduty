package com.example.pagerduty.dto;

import org.springframework.stereotype.Component;
import com.example.pagerduty.model.ServiceEntity;

@Component
public class ServiceMapper {

    public ServiceDto toDto(ServiceEntity entity) {
        ServiceDto dto = new ServiceDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSummary(entity.getSummary());
        dto.setType(entity.getType());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setSelf(entity.getSelfUrl());
        dto.setHtmlUrl(entity.getHtmlUrl());
        dto.setAutoResolveTimeout(entity.getAutoResolveTimeout());
        dto.setAcknowledgementTimeout(entity.getAcknowledgementTimeout());
        dto.setAlertCreation(entity.getAlertCreation());

        if (entity.getEscalationPolicy() != null) {
            EscalationPolicyDto ep = new EscalationPolicyDto();
            ep.setId(entity.getEscalationPolicy().getId());
            ep.setSummary(entity.getEscalationPolicy().getSummary());
            ep.setType("escalation_policy_reference");
            ep.setHtmlUrl(entity.getEscalationPolicy().getHtmlUrl());
            ep.setSelf(entity.getEscalationPolicy().getSelfUrl());
            dto.setEscalationPolicy(ep);
        }

        if (entity.getTeams() != null) {
            dto.setTeams(entity.getTeams().stream().distinct().map(team -> {
                TeamDto t = new TeamDto();
                t.setId(team.getId());
                t.setSummary(team.getSummary());
                t.setType("team_reference");
                t.setHtmlUrl(team.getHtmlUrl());
                t.setSelf(team.getSelfUrl());
                t.setName(team.getName());
                t.setDescription(team.getDescription());
                return t;
            }).toList());
        }
        return dto;
    }
}