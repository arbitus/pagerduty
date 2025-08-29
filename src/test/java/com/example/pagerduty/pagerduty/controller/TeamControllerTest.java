package com.example.pagerduty.pagerduty.controller;

import com.example.pagerduty.repository.TeamRepository;
import com.example.pagerduty.controller.TeamController;
import com.example.pagerduty.model.TeamEntity;
import com.example.pagerduty.dto.TeamMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collections;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TeamController.class)
class TeamControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TeamRepository teamRepository;
    @MockBean
    private TeamMapper teamMapper;

    @Test
    void testGetAllTeams() throws Exception {
        TeamEntity team = new TeamEntity();
        team.setId("team1");
        team.setSummary("Team");
        org.springframework.data.domain.PageImpl<TeamEntity> page = new org.springframework.data.domain.PageImpl<>(
                Collections.singletonList(team));
        Mockito.when(teamRepository.findAll(Mockito.any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(page);
        com.example.pagerduty.dto.TeamDto dto = new com.example.pagerduty.dto.TeamDto();
        dto.setId("team1");
        dto.setSummary("Team");
        Mockito.when(teamMapper.toDto(team)).thenReturn(dto);
        mockMvc.perform(get("/api/v1/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("team1"));
    }

    @Test
    void testUnauthorizedException() throws Exception {
        Mockito.when(teamRepository.findAll(Mockito.any(org.springframework.data.domain.Pageable.class)))
                .thenThrow(new com.example.pagerduty.exception.UnauthorizedException("Unauthorized access"));
        mockMvc.perform(get("/api/v1/teams"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testTooManyRequestsException() throws Exception {
        Mockito.when(teamRepository.findAll(Mockito.any(org.springframework.data.domain.Pageable.class)))
                .thenThrow(new com.example.pagerduty.exception.TooManyRequestsException("Rate limit exceeded"));
        mockMvc.perform(get("/api/v1/teams"))
                .andExpect(status().isTooManyRequests());
    }
}
