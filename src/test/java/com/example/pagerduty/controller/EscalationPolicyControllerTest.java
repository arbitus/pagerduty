package com.example.pagerduty.controller;

import com.example.pagerduty.repository.EscalationPolicyRepository;
import com.example.pagerduty.controller.EscalationPolicyController;
import com.example.pagerduty.model.EscalationPolicyEntity;
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

@WebMvcTest(EscalationPolicyController.class)
class EscalationPolicyControllerTest {
        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private EscalationPolicyRepository escalationPolicyRepository;

        @Test
        void testGetAllEscalationPolicies() throws Exception {
                EscalationPolicyEntity policy = new EscalationPolicyEntity();
                policy.setId("ep1");
                policy.setSummary("Policy");
                org.springframework.data.domain.PageImpl<EscalationPolicyEntity> page = new org.springframework.data.domain.PageImpl<>(
                                Collections.singletonList(policy));
                Mockito.when(escalationPolicyRepository
                                .findAll(Mockito.any(org.springframework.data.domain.Pageable.class)))
                                .thenReturn(page);
                mockMvc.perform(get("/api/v1/escalation-policies"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content[0].id").value("ep1"));
        }
}
