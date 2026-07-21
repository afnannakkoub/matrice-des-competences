package com.matrice.backend.controller;
import com.matrice.backend.entity.Competence;
import com.matrice.backend.service.CompetenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/competences")
public class CompetenceController {

    private final CompetenceService service;

    public CompetenceController(CompetenceService service) {
        this.service = service;
    }

    @GetMapping
    public List<Competence> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Competence> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Competence create(@RequestBody Competence competence) {
        return service.save(competence);
    }

    @PutMapping("/{id}")
    public Competence update(@PathVariable Long id,
                             @RequestBody Competence competence) {
        return service.update(id, competence);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}