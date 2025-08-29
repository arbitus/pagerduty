package com.example.pagerduty.dto;

import java.time.Instant;
import java.util.List;

public class ServiceResponseDto {
    private List<ServiceDto> services;
    private int limit;
    private int offset;
    private boolean more;
    private Integer total;

    public List<ServiceDto> getServices() {
        return services;
    }

    public void setServices(List<ServiceDto> services) {
        this.services = services;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public static class ServiceDto {
        private String id;
        private String name;
        private String summary;
        private String type;
        private String self;
        private String html_url;
        private String status;
        private Integer auto_resolve_timeout;
        private Integer acknowledgement_timeout;
        private String alert_creation;
        private Instant created_at;

        private EscalationPolicyDto escalation_policy;
        private List<TeamDto> teams;

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

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getAuto_resolve_timeout() {
            return auto_resolve_timeout;
        }

        public void setAuto_resolve_timeout(Integer auto_resolve_timeout) {
            this.auto_resolve_timeout = auto_resolve_timeout;
        }

        public Integer getAcknowledgement_timeout() {
            return acknowledgement_timeout;
        }

        public void setAcknowledgement_timeout(Integer acknowledgement_timeout) {
            this.acknowledgement_timeout = acknowledgement_timeout;
        }

        public String getAlert_creation() {
            return alert_creation;
        }

        public void setAlert_creation(String alert_creation) {
            this.alert_creation = alert_creation;
        }

        public Instant getCreated_at() {
            return created_at;
        }

        public void setCreated_at(Instant created_at) {
            this.created_at = created_at;
        }

        public EscalationPolicyDto getEscalation_policy() {
            return escalation_policy;
        }

        public void setEscalation_policy(EscalationPolicyDto escalation_policy) {
            this.escalation_policy = escalation_policy;
        }

        public List<TeamDto> getTeams() {
            return teams;
        }

        public void setTeams(List<TeamDto> teams) {
            this.teams = teams;
        }
    }

    public static class EscalationPolicyDto {
        private String id;
        private String type;
        private String summary;
        private String self;
        private String html_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }
    }

    public static class TeamDto {
        private String id;
        private String type;
        private String summary;
        private String self;
        private String html_url;
        private String name;
        private String description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}