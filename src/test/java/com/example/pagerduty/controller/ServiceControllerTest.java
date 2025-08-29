package com.example.pagerduty.controller;

import com.example.pagerduty.repository.ServiceRepository;
import com.example.pagerduty.controller.ServiceController;
import com.example.pagerduty.model.ServiceEntity;
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

@WebMvcTest(ServiceController.class)
class ServiceControllerTest {
        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private ServiceRepository serviceRepository;
        @MockBean
        private com.example.pagerduty.dto.ServiceMapper serviceMapper;

        @Test
        void testGetAllServices() throws Exception {
                ServiceEntity service = new ServiceEntity();
                service.setId("svc1");
                service.setName("Service 1");
                org.springframework.data.domain.PageImpl<ServiceEntity> page = new org.springframework.data.domain.PageImpl<>(
                                Collections.singletonList(service));
                Mockito.when(serviceRepository.findAll(Mockito.any(org.springframework.data.domain.Pageable.class)))
                                .thenReturn(page);
                com.example.pagerduty.dto.ServiceResponseDto.ServiceDto dto = new com.example.pagerduty.dto.ServiceResponseDto.ServiceDto();
                dto.setId("svc1");
                dto.setName("Service 1");
                Mockito.when(serviceMapper.toDto(Mockito.any(ServiceEntity.class))).thenReturn(dto);
                mockMvc.perform(get("/api/v1/services"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.services[0].id").value("svc1"));
        }
}
