package com.example.pagerduty.controller;

import com.example.pagerduty.controller.PagerDutyController;
import com.example.pagerduty.service.PagerDutySyncService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PagerDutyController.class)
class PagerDutyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PagerDutySyncService syncService;

    @Test
    void testSyncPagerDutyEndpoint() throws Exception {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        Mockito.when(syncService.syncPagerDuty()).thenReturn(result);
        mockMvc.perform(post("/sync/pagerduty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));
    }
}
