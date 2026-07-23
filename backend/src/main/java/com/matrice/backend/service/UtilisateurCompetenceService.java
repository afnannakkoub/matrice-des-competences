package com.matrice.backend.service;
import com.matrice.backend.entity.Competence;
import com.matrice.backend.entity.StatutEvaluation;
import com.matrice.backend.entity.Utilisateur;
import com.matrice.backend.DTO.*;
import com.matrice.backend.entity.UtilisateurCompetence;
import com.matrice.backend.repository.CompetenceRepository;
import com.matrice.backend.repository.UtilisateurCompetenceRepository;
import com.matrice.backend.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
@Service
public class UtilisateurCompetenceService {

    private final UtilisateurCompetenceRepository repository;
    private final UtilisateurRepository utilisateurRepository;
    private final CompetenceRepository competenceRepository;

    public UtilisateurCompetenceService(
            UtilisateurCompetenceRepository repository,
            UtilisateurRepository utilisateurRepository,
            CompetenceRepository competenceRepository) {

        this.repository = repository;
        this.utilisateurRepository = utilisateurRepository;
        this.competenceRepository = competenceRepository;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<UtilisateurCompetence> getAll() {
        return repository.findAll();
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    public UtilisateurCompetence getById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Evaluation not found with id : " + id));
    }

    // =====================================================
    // CREATE
    // =====================================================

    public UtilisateurCompetence save(UtilisateurCompetence uc) {

        verifierNiveau(uc.getNiveauPropose());

        if (uc.getUtilisateur() == null || uc.getUtilisateur().getId() == null) {
            throw new RuntimeException("Utilisateur obligatoire.");
        }

        if (uc.getCompetence() == null || uc.getCompetence().getId() == null) {
            throw new RuntimeException("Compétence obligatoire.");
        }

        verifierDoublon(
                uc.getUtilisateur().getId(),
                uc.getCompetence().getId()
        );

        Utilisateur utilisateur = utilisateurRepository
                .findById(uc.getUtilisateur().getId())
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur introuvable."));

        Competence competence = competenceRepository
                .findById(uc.getCompetence().getId())
                .orElseThrow(() ->
                        new RuntimeException("Compétence introuvable."));

        uc.setUtilisateur(utilisateur);
        uc.setCompetence(competence);

        uc.setStatut(StatutEvaluation.EN_ATTENTE);
        uc.setNiveauValide(null);
        uc.setManager(null);
        uc.setDateCreation(LocalDateTime.now());
        uc.setDateValidation(null);

        return repository.save(uc);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public UtilisateurCompetence update(Long id,
                                        UtilisateurCompetence updated) {

        verifierNiveau(updated.getNiveauPropose());

        UtilisateurCompetence uc = getById(id);

        uc.setNiveauPropose(updated.getNiveauPropose());

        // Must be validated again
        uc.setStatut(StatutEvaluation.EN_ATTENTE);
        uc.setNiveauValide(null);
        uc.setManager(null);
        uc.setDateValidation(null);

        return repository.save(uc);
    }

    // =====================================================
    // DELETE
    // =====================================================

    public void delete(Long id) {

        getById(id);

        repository.deleteById(id);
    }

    // =====================================================
    // EMPLOYEE
    // =====================================================

    public List<UtilisateurCompetence> getByUtilisateur(Long utilisateurId) {

        return repository.findByUtilisateurId(utilisateurId);

    }

    // =====================================================
    // MANAGER
    // =====================================================

    public List<UtilisateurCompetence> getPending() {

        return repository.findByStatut(StatutEvaluation.EN_ATTENTE);

    }

    public List<UtilisateurCompetence> getByManager(Long managerId) {

        return repository.findByManagerId(managerId);

    }

    // =====================================================
    // VALIDATE
    // =====================================================

    public UtilisateurCompetence validate(
            Long id,
            Integer niveau,
            Long managerId) {

        verifierNiveau(niveau);

        UtilisateurCompetence uc = getById(id);

        Utilisateur manager = utilisateurRepository
                .findById(managerId)
                .orElseThrow(() ->
                        new RuntimeException("Manager introuvable."));

        uc.setManager(manager);
        uc.setNiveauValide(niveau);
        uc.setStatut(StatutEvaluation.VALIDE);
        uc.setDateValidation(LocalDateTime.now());

        return repository.save(uc);
    }

    // =====================================================
    // PRIVATE METHODS
    // =====================================================

    private void verifierNiveau(Integer niveau) {

        if (niveau == null || niveau < 0 || niveau > 4) {

            throw new RuntimeException(
                    "Le niveau doit être compris entre 0 et 4.");

        }

    }

    private void verifierDoublon(Long utilisateurId,
                                 Long competenceId) {

        if (repository.findByUtilisateurIdAndCompetenceId(
                utilisateurId,
                competenceId).isPresent()) {

            throw new RuntimeException(
                    "Cette compétence est déjà déclarée pour cet utilisateur.");

        }

    }

    // =====================================================
// MANAGER
// Pending validations
// =====================================================

    public List<PendingValidationDTO> getPendingValidations(Long managerId) {

        List<UtilisateurCompetence> evaluations =
                repository.findByUtilisateurManagerIdAndStatut(
                        managerId,
                        StatutEvaluation.EN_ATTENTE);

        List<PendingValidationDTO> result = new ArrayList<>();

        for (UtilisateurCompetence evaluation : evaluations) {

            PendingValidationDTO dto = new PendingValidationDTO();

            dto.setEvaluationId(evaluation.getId());

            dto.setEmploye(
                    evaluation.getUtilisateur().getNom()
                            + " "
                            + evaluation.getUtilisateur().getPrenom()
            );

            dto.setCompetence(
                    evaluation.getCompetence().getNom()
            );

            dto.setNiveauPropose(
                    evaluation.getNiveauPropose()
            );

            result.add(dto);
        }

        return result;
    }



}

