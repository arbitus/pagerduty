package com.example.pagerduty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pagerduty.model.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {
}
