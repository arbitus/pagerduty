package com.example.pagerduty.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;

@Entity
@Table(name = "services")
public class ServiceEntity {
    @Id
    private String id;
    private String name;
    private String summary;
    private String type;
    private String selfUrl;
    private String htmlUrl;
    private String status;
    private Integer autoResolveTimeout;
    private Integer acknowledgementTimeout;
    private String alertCreation;
    private java.time.Instant createdAt;
    private String description;

    @ManyToOne
    @JoinColumn(name = "escalation_policy_id")
    private EscalationPolicyEntity escalationPolicy;

    @ManyToMany
    @JoinTable(name = "service_team", joinColumns = @JoinColumn(name = "service_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    @JsonIgnoreProperties("services")
    private Set<TeamEntity> teams = new java.util.HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSelfUrl() {
        return selfUrl;
    }

    public void setSelfUrl(String selfUrl) {
        this.selfUrl = selfUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAutoResolveTimeout() {
        return autoResolveTimeout;
    }

    public void setAutoResolveTimeout(Integer autoResolveTimeout) {
        this.autoResolveTimeout = autoResolveTimeout;
    }

    public Integer getAcknowledgementTimeout() {
        return acknowledgementTimeout;
    }

    public void setAcknowledgementTimeout(Integer acknowledgementTimeout) {
        this.acknowledgementTimeout = acknowledgementTimeout;
    }

    public String getAlertCreation() {
        return alertCreation;
    }

    public void setAlertCreation(String alertCreation) {
        this.alertCreation = alertCreation;
    }

    public java.time.Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EscalationPolicyEntity getEscalationPolicy() {
        return escalationPolicy;
    }

    public void setEscalationPolicy(EscalationPolicyEntity escalationPolicy) {
        this.escalationPolicy = escalationPolicy;
    }

    public Set<TeamEntity> getTeams() {
        return teams;
    }

    public void setTeams(Set<TeamEntity> teams) {
        this.teams = teams;
    }
}
