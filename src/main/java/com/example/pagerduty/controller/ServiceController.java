package com.example.pagerduty.controller;

import com.example.pagerduty.dto.ServiceMapper;
import com.example.pagerduty.dto.ServiceResponseDto;
import com.example.pagerduty.model.ServiceEntity;
import com.example.pagerduty.repository.ServiceRepository;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {
    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    private final ServiceRepository repository;
    private final ServiceMapper mapper;

    public ServiceController(ServiceRepository repository, ServiceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ServiceResponseDto> getAll(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        try {
            Page<ServiceEntity> page = repository.findAll(pageable);
            List<ServiceResponseDto.ServiceDto> serviceDtos = page.getContent().stream()
                    .map(mapper::toDto)
                    .toList();
            ServiceResponseDto response = new ServiceResponseDto();
            response.setServices(serviceDtos);
            response.setLimit(pageable.getPageSize());
            response.setOffset((int) pageable.getOffset());
            response.setMore(page.hasNext());
            response.setTotal((int) page.getTotalElements());
            return ResponseEntity.ok(response);
        } catch (com.example.pagerduty.exception.UnauthorizedException ex) {
            logger.warn("Unauthorized access: {}", ex.getMessage());
            throw ex;
        } catch (com.example.pagerduty.exception.TooManyRequestsException ex) {
            logger.warn("Rate limit exceeded: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            logger.error("Internal error: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
