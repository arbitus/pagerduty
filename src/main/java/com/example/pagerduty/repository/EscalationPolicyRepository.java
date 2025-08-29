package com.example.pagerduty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pagerduty.model.EscalationPolicyEntity;

public interface EscalationPolicyRepository extends JpaRepository<EscalationPolicyEntity, String> {
}
