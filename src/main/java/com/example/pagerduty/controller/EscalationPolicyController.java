package com.example.pagerduty.controller;

import com.example.pagerduty.model.EscalationPolicyEntity;
import com.example.pagerduty.repository.EscalationPolicyRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/escalation-policies")
public class EscalationPolicyController {

    private final EscalationPolicyRepository repository;

    public EscalationPolicyController(EscalationPolicyRepository repository) {
        this.repository = repository;
    }

    // GET con paginación y orden
    @GetMapping
    public ResponseEntity<Page<EscalationPolicyEntity>> getAll(
            @PageableDefault(size = 20, sort = "summary") Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<EscalationPolicyEntity> getById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
