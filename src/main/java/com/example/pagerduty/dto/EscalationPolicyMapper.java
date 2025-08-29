package com.example.pagerduty.dto;

import org.springframework.stereotype.Component;
import com.example.pagerduty.model.EscalationPolicyEntity;

@Component
public class EscalationPolicyMapper {
    public EscalationPolicyDto toDto(EscalationPolicyEntity entity) {
        EscalationPolicyDto dto = new EscalationPolicyDto();
        dto.setId(entity.getId());
        dto.setSummary(entity.getSummary());
        dto.setType(entity.getType());
        dto.setHtmlUrl(entity.getHtmlUrl());
        dto.setSelf(entity.getSelfUrl());
        return dto;
    }
}
