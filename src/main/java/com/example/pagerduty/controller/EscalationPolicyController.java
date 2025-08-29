package com.example.pagerduty.controller;

import com.example.pagerduty.repository.EscalationPolicyRepository;
import com.example.pagerduty.dto.EscalationPolicyDto;
import com.example.pagerduty.dto.EscalationPolicyMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Escalation Policies.
 */
@RestController
@RequestMapping("/api/v1/escalation-policies")
public class EscalationPolicyController {

    private final EscalationPolicyRepository repository;
    private final EscalationPolicyMapper mapper;

    public EscalationPolicyController(EscalationPolicyRepository repository, EscalationPolicyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Returns a paginated list of escalation policies.
     * Allows client to specify page, size, and sort via query params.
     * 
     * @param pageable Pageable object from request
     * @return Page of EscalationPolicyEntity
     */
    @GetMapping
    public ResponseEntity<Page<EscalationPolicyDto>> getAll(Pageable pageable) {
        Page<EscalationPolicyDto> dtoPage = repository.findAll(pageable)
                .map(mapper::toDto);
        return ResponseEntity.ok(dtoPage);
    }

    /**
     * Returns an escalation policy by its ID.
     * 
     * @param id Policy ID (must be alphanumeric)
     * @return EscalationPolicyEntity or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<EscalationPolicyDto> getById(@PathVariable String id) {
        if (!id.matches("^[a-zA-Z0-9_-]+$")) {
            return ResponseEntity.badRequest().build();
        }
        return repository.findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
