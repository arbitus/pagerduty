package com.example.pagerduty.controller;

import com.example.pagerduty.repository.TeamRepository;
import com.example.pagerduty.controller.TeamController;
import com.example.pagerduty.model.TeamEntity;
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

    @Test
    void testGetAllTeams() throws Exception {
        TeamEntity team = new TeamEntity();
        team.setId("team1");
        team.setSummary("Team");
        Mockito.when(teamRepository.findAll()).thenReturn(Collections.singletonList(team));
        mockMvc.perform(get("/api/v1/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("team1"));
    }
}
