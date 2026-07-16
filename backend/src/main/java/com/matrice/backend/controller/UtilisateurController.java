package com.matrice.backend.controller;

import com.matrice.backend.entity.Utilisateur;
import com.matrice.backend.service.UtilisateurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService service;

    public UtilisateurController(UtilisateurService service) {
        this.service = service;
    }

    // ==========================
    // GET ALL
    // ==========================
    @GetMapping
    public List<Utilisateur> getAll() {
        return service.getAllUtilisateurs();
    }

    // ==========================
    // GET BY ID
    // ==========================
    @GetMapping("/{id}")
    public Optional<Utilisateur> getById(@PathVariable Long id) {
        return service.getUtilisateurById(id);
    }

    // ==========================
    // CREATE
    // ==========================
    @PostMapping
    public Utilisateur create(@RequestBody Utilisateur utilisateur) {
        return service.save(utilisateur);
    }

    // ==========================
    // UPDATE
    // ==========================
    @PutMapping("/{id}")
    public Utilisateur update(
            @PathVariable Long id,
            @RequestBody Utilisateur utilisateur) {

        return service.update(id, utilisateur);
    }

    // ==========================
    // DELETE
    // ==========================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
