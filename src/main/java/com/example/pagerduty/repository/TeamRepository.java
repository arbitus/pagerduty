package com.example.pagerduty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pagerduty.model.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, String> {
}
