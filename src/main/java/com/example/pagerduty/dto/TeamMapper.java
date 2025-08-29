package com.example.pagerduty.dto;

import org.springframework.stereotype.Component;
import com.example.pagerduty.model.TeamEntity;

@Component
public class TeamMapper {
    public TeamDto toDto(TeamEntity entity) {
        TeamDto dto = new TeamDto();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setSummary(entity.getSummary());
        dto.setSelf(entity.getSelfUrl());
        dto.setHtmlUrl(entity.getHtmlUrl());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
