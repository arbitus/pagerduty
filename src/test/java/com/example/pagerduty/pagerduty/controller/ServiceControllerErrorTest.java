package com.example.pagerduty.pagerduty.controller;

import com.example.pagerduty.controller.ServiceController;
import com.example.pagerduty.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServiceController.class)
class ServiceControllerErrorTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServiceRepository serviceRepository;
    @MockBean
    private com.example.pagerduty.dto.ServiceMapper serviceMapper;

    @Test
    void testInternalServerError() throws Exception {
        Mockito.when(serviceRepository.findAll(Mockito.any(org.springframework.data.domain.Pageable.class)))
                .thenThrow(new RuntimeException("DB error"));
        mockMvc.perform(get("/api/v1/services"))
                .andExpect(status().is5xxServerError());
    }
}
