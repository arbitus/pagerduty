package com.example.pagerduty.controller;

import com.example.pagerduty.model.TeamEntity;
import com.example.pagerduty.repository.TeamRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamRepository repository;

    public TeamController(TeamRepository repository) {
        this.repository = repository;
    }

    // GET todos los equipos
    @GetMapping
    public ResponseEntity<List<TeamEntity>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<TeamEntity> getById(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
