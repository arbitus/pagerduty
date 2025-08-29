package com.example.pagerduty.dto;

import java.time.Instant;
import java.util.List;

public class ServiceDto {
    private String id;
    private String name;
    private String summary;
    private String type;
    private String self;
    private String htmlUrl;
    private String status;
    private Integer autoResolveTimeout;
    private Integer acknowledgementTimeout;
    private String alertCreation;
    private Instant createdAt;
    private EscalationPolicyDto escalationPolicy;
    private List<TeamDto> teams;

    // Getters y setters
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

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public EscalationPolicyDto getEscalationPolicy() {
        return escalationPolicy;
    }

    public void setEscalationPolicy(EscalationPolicyDto escalationPolicy) {
        this.escalationPolicy = escalationPolicy;
    }

    public List<TeamDto> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDto> teams) {
        this.teams = teams;
    }
}
