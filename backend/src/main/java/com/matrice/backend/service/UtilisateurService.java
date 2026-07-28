package com.matrice.backend.service;
import com.matrice.backend.DTO.*;
import com.matrice.backend.DTO.PendingValidationDTO;
import com.matrice.backend.entity.StatutEvaluation;
import com.matrice.backend.entity.*;
import com.matrice.backend.repository.UtilisateurCompetenceRepository;
import com.matrice.backend.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.matrice.backend.DTO.SkillMatrixRowDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service

public class UtilisateurService {
    private final UtilisateurRepository repository;
    private final UtilisateurCompetenceRepository competenceRepository;



    public UtilisateurService(
            UtilisateurRepository repository,
            UtilisateurCompetenceRepository competenceRepository) {

        this.repository = repository;
        this.competenceRepository = competenceRepository;
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

// =========================================
// MANAGER
// Get team
// =========================================

    public List<Utilisateur> getEquipe(Long managerId) {

        return repository.findByManagerId(managerId);

    }




    public ManagerDashboardDTO getDashboard(Long managerId) {

        Utilisateur manager = repository.findById(managerId)
                .orElseThrow(() ->
                        new RuntimeException("Manager introuvable"));

        Long nbEmployes = repository.countByManagerId(managerId);

        Long enAttente =
                competenceRepository
                        .countByUtilisateurManagerIdAndStatut(
                                managerId,
                                StatutEvaluation.EN_ATTENTE);

        Long validees =
                competenceRepository
                        .countByManagerId(managerId);

        return new ManagerDashboardDTO(

                manager.getNom() + " " + manager.getPrenom(),

                nbEmployes,

                enAttente,

                validees

        );

    }
    // =========================================
   // TEAM SKILL MATRIX
    // =========================================

    public List<SkillMatrixRowDTO> getSkillMatrix(Long managerId) {

        List<Utilisateur> equipe = repository.findByManagerId(managerId);

        List<SkillMatrixRowDTO> matrix = new ArrayList<>();

        for (Utilisateur utilisateur : equipe) {

            List<UtilisateurCompetence> evaluations =
                    competenceRepository.findByUtilisateurId(
                            utilisateur.getId());

            Map<String, Integer> competences = new HashMap<>();

            for (UtilisateurCompetence evaluation : evaluations) {

                if (evaluation.getStatut() == StatutEvaluation.VALIDE) {

                    competences.put(

                            evaluation.getCompetence().getNom(),

                            evaluation.getNiveauValide()

                    );

                }

            }

            SkillMatrixRowDTO row = new SkillMatrixRowDTO();

            row.setUtilisateurId(utilisateur.getId());

            row.setNom(utilisateur.getNom());

            row.setPrenom(utilisateur.getPrenom());

            row.setCompetences(competences);

            matrix.add(row);

        }

        return matrix;

    }

}
