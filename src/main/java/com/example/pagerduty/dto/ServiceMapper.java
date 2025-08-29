package com.example.pagerduty.dto;

import org.springframework.stereotype.Component;
import com.example.pagerduty.model.ServiceEntity;

@Component
public class ServiceMapper {

    public ServiceResponseDto.ServiceDto toDto(ServiceEntity entity) {
        ServiceResponseDto.ServiceDto dto = new ServiceResponseDto.ServiceDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSummary(entity.getSummary());
        dto.setStatus(entity.getStatus());
        dto.setCreated_at(entity.getCreatedAt());

        if (entity.getEscalationPolicy() != null) {
            ServiceResponseDto.EscalationPolicyDto ep = new ServiceResponseDto.EscalationPolicyDto();
            ep.setId(entity.getEscalationPolicy().getId());
            ep.setSummary(entity.getEscalationPolicy().getSummary());
            ep.setType("escalation_policy_reference");
            ep.setHtml_url(entity.getEscalationPolicy().getHtmlUrl());
            ep.setSelf(entity.getEscalationPolicy().getSelfUrl());
            dto.setEscalation_policy(ep);
        }

        if (entity.getTeams() != null) {
            dto.setTeams(entity.getTeams().stream().distinct().map(team -> {
                ServiceResponseDto.TeamDto t = new ServiceResponseDto.TeamDto();
                t.setId(team.getId());
                t.setSummary(team.getSummary());
                t.setType("team_reference");
                t.setHtml_url(team.getHtmlUrl());
                t.setSelf(team.getSelfUrl());
                return t;
            }).toList());
        }
        return dto;
    }
}