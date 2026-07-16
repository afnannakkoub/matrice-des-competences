package com.matrice.backend.service;
import com.matrice.backend.entity.Utilisateur;
import com.matrice.backend.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UtilisateurService {
    private final UtilisateurRepository repository;

    public UtilisateurService(UtilisateurRepository repository) {
        this.repository = repository;
    }

    // =========================
    // CREATE
    // =========================
    public Utilisateur save(Utilisateur utilisateur) {

        if(repository.existsByEmail(utilisateur.getEmail())){
            throw new RuntimeException("Email already exists.");
        }

        return repository.save(utilisateur);
    }

    // =========================
    // READ ALL
    // =========================
    public List<Utilisateur> getAllUtilisateurs() {
        return repository.findAll();
    }

    // =========================
    // READ BY ID
    // =========================
    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return repository.findById(id);
    }

    // =========================
    // UPDATE
    // =========================
    public Utilisateur update(Long id, Utilisateur utilisateur) {

        Utilisateur existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        existing.setNom(utilisateur.getNom());
        existing.setPrenom(utilisateur.getPrenom());
        existing.setEmail(utilisateur.getEmail());
        existing.setMotDePasse(utilisateur.getMotDePasse());
        existing.setRole(utilisateur.getRole());
        existing.setManager(utilisateur.getManager());
        existing.setActif(utilisateur.getActif());
        existing.setPoste(utilisateur.getPoste());
        existing.setDepartement(utilisateur.getDepartement());

        return repository.save(existing);
    }

    // =========================
    // DELETE
    // =========================
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
