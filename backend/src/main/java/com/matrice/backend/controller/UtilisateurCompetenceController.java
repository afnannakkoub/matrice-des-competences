package com.matrice.backend.controller;

import com.matrice.backend.entity.UtilisateurCompetence;
import com.matrice.backend.service.UtilisateurCompetenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateur-competences")
public class UtilisateurCompetenceController {

    private final UtilisateurCompetenceService service;

    public UtilisateurCompetenceController(UtilisateurCompetenceService service) {
        this.service = service;
    }

    // ============================================
    // GET ALL
    // ============================================

    @GetMapping
    public List<UtilisateurCompetence> getAll() {
        return service.getAll();
    }

    // ============================================
    // GET BY ID
    // ============================================

    @GetMapping("/{id}")
    public UtilisateurCompetence getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // ============================================
    // EMPLOYEE
    // Add a new declared skill
    // ============================================

    @PostMapping
    public UtilisateurCompetence create(
            @RequestBody UtilisateurCompetence utilisateurCompetence) {

        return service.save(utilisateurCompetence);
    }

    // ============================================
    // EMPLOYEE
    // Update his declaration
    // ============================================

    @PutMapping("/{id}")
    public UtilisateurCompetence update(
            @PathVariable Long id,
            @RequestBody UtilisateurCompetence utilisateurCompetence) {

        return service.update(id, utilisateurCompetence);
    }

    // ============================================
    // DELETE
    // ============================================

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // ============================================
    // EMPLOYEE
    // View all his skills
    // ============================================

    @GetMapping("/utilisateur/{id}")
    public List<UtilisateurCompetence> getByUtilisateur(
            @PathVariable Long id) {

        return service.getByUtilisateur(id);
    }

    // ============================================
    // MANAGER
    // View evaluations waiting validation
    // ============================================

    @GetMapping("/en-attente")
    public List<UtilisateurCompetence> getPending() {

        return service.getPending();
    }

    // ============================================
    // MANAGER
    // View validations made by manager
    // ============================================

    @GetMapping("/manager/{id}")
    public List<UtilisateurCompetence> getByManager(
            @PathVariable Long id) {

        return service.getByManager(id);
    }

    // ============================================
    // MANAGER
    // Validate employee level
    // ============================================

    @PutMapping("/{id}/valider")
    public UtilisateurCompetence validateSkill(

            @PathVariable Long id,

            @RequestParam Integer niveau,

            @RequestParam Long managerId) {

        return service.validate(id, niveau, managerId);
    }

}