package com.matrice.backend.controller;

import com.matrice.backend.entity.PosteCompetence;
import com.matrice.backend.service.PosteCompetenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/poste-competences")
public class PosteCompetenceController {

    private final PosteCompetenceService service;

    public PosteCompetenceController(PosteCompetenceService service) {
        this.service = service;
    }

    // =========================
    // GET ALL
    // =========================

    @GetMapping
    public List<PosteCompetence> getAll() {

        return service.getAll();

    }

    // =========================
    // GET BY ID
    // =========================

    @GetMapping("/{id}")
    public Optional<PosteCompetence> getById(
            @PathVariable Long id) {

        return service.getById(id);

    }

    // =========================
    // GET BY POSITION
    // =========================

    @GetMapping("/poste/{poste}")
    public List<PosteCompetence> getByPoste(
            @PathVariable String poste) {

        return service.getByPoste(poste);

    }

    // =========================
    // CREATE
    // =========================

    @PostMapping
    public PosteCompetence create(
            @RequestBody PosteCompetence posteCompetence) {

        return service.save(posteCompetence);

    }

    // =========================
    // UPDATE
    // =========================

    @PutMapping("/{id}")
    public PosteCompetence update(
            @PathVariable Long id,
            @RequestBody PosteCompetence posteCompetence) {

        return service.update(id, posteCompetence);

    }

    // =========================
    // DELETE
    // =========================

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        service.delete(id);

    }

}