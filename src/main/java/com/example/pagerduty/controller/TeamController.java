package com.example.pagerduty.controller;

import com.example.pagerduty.repository.TeamRepository;
import com.example.pagerduty.dto.TeamDto;
import com.example.pagerduty.dto.TeamMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamRepository repository;
    private final TeamMapper mapper;

    public TeamController(TeamRepository repository, TeamMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Returns a paginated list of teams as DTOs.
     * Allows client to specify page, size, and sort via query params.
     * 
     * @param pageable Pageable object from request
     * @return Page of TeamDto
     */
    @GetMapping
    public ResponseEntity<Page<TeamDto>> getAll(Pageable pageable) {
        Page<TeamDto> dtoPage = repository.findAll(pageable)
                .map(mapper::toDto);
        return ResponseEntity.ok(dtoPage);
    }

    /**
     * Returns a team by its ID.
     * 
     * @param id Team ID (must be alphanumeric)
     * @return TeamDto or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getById(@PathVariable String id) {
        if (!id.matches("^[a-zA-Z0-9_-]+$")) {
            return ResponseEntity.badRequest().build();
        }
        return repository.findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
