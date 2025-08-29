package com.example.pagerduty.controller;

import com.example.pagerduty.dto.ServiceMapper;
import com.example.pagerduty.dto.ServiceDto;
import com.example.pagerduty.repository.ServiceRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Services.
 */
@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {
    private final ServiceRepository repository;
    private final ServiceMapper mapper;

    public ServiceController(ServiceRepository repository, ServiceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Returns a paginated list of services as DTOs.
     * Allows client to specify page, size, and sort via query params.
     * 
     * @param pageable Pageable object from request
     * @return Page of ServiceDto
     */
    @GetMapping
    public ResponseEntity<Page<ServiceDto>> getAll(Pageable pageable) {
        Page<ServiceDto> dtoPage = repository.findAll(pageable)
                .map(mapper::toDto);
        return ResponseEntity.ok(dtoPage);
    }
}
