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
import com.matrice.backend.DTO.ManagerEvaluationDTO;
import com.matrice.backend.entity.PosteCompetence;
import com.matrice.backend.repository.PosteCompetenceRepository;


import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UtilisateurCompetenceService {

    private final UtilisateurCompetenceRepository repository;
    private final UtilisateurRepository utilisateurRepository;
    private final CompetenceRepository competenceRepository;
    private final PosteCompetenceRepository posteCompetenceRepository;


    public UtilisateurCompetenceService(
            UtilisateurCompetenceRepository repository,
            UtilisateurRepository utilisateurRepository,
            CompetenceRepository competenceRepository,
            PosteCompetenceRepository posteCompetenceRepository) {

        this.repository = repository;
        this.utilisateurRepository = utilisateurRepository;
        this.competenceRepository = competenceRepository;
        this.posteCompetenceRepository = posteCompetenceRepository;
    }

    public UtilisateurCompetence managerEvaluate(
            Long utilisateurId,
            Long competenceId,
            Integer niveau,
            Long managerId) {

        // =========================================
        // 1. Verify level
        // =========================================

        verifierNiveau(niveau);


        // =========================================
        // 2. Find employee
        // =========================================

        Utilisateur utilisateur =
                utilisateurRepository
                        .findById(utilisateurId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Utilisateur introuvable."
                                ));


        // =========================================
        // 3. Find competence
        // =========================================

        Competence competence =
                competenceRepository
                        .findById(competenceId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Compétence introuvable."
                                ));


        // =========================================
        // 4. Find manager
        // =========================================

        Utilisateur manager =
                utilisateurRepository
                        .findById(managerId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Manager introuvable."
                                ));


        // =========================================
        // 5. Check if evaluation already exists
        // =========================================

        Optional<UtilisateurCompetence> existing =
                repository.findByUtilisateurIdAndCompetenceId(
                        utilisateurId,
                        competenceId
                );


        // =========================================
        // 6. EXISTING evaluation
        // =========================================

        if (existing.isPresent()) {

            UtilisateurCompetence uc =
                    existing.get();

            uc.setNiveauValide(niveau);

            uc.setStatut(
                    StatutEvaluation.VALIDE
            );

            uc.setManager(manager);

            uc.setDateValidation(
                    LocalDateTime.now()
            );

            return repository.save(uc);
        }


        // =========================================
        // 7. CREATE new evaluation
        // =========================================

        UtilisateurCompetence uc =
                new UtilisateurCompetence();

        uc.setUtilisateur(utilisateur);

        uc.setCompetence(competence);

        /*
         * Because niveauEmploye is nullable = false
         * in the entity/database, we use the manager's
         * evaluated level here when there was no
         * employee declaration.
         */
        uc.setNiveauEmploye(niveau);

        uc.setNiveauValide(niveau);

        uc.setStatut(
                StatutEvaluation.VALIDE
        );

        uc.setManager(manager);

        uc.setDateCreation(
                LocalDateTime.now()
        );

        uc.setDateValidation(
                LocalDateTime.now()
        );

        return repository.save(uc);
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
                        new RuntimeException(
                                "Evaluation not found with id : " + id
                        ));
    }

    // =====================================================
    // CREATE
    // Employee declares a competence
    // =====================================================

    public UtilisateurCompetence save(UtilisateurCompetence uc) {

        verifierNiveau(uc.getNiveauEmploye());

        if (uc.getUtilisateur() == null ||
                uc.getUtilisateur().getId() == null) {

            throw new RuntimeException(
                    "Utilisateur obligatoire."
            );
        }

        if (uc.getCompetence() == null ||
                uc.getCompetence().getId() == null) {

            throw new RuntimeException(
                    "Compétence obligatoire."
            );
        }

        verifierDoublon(
                uc.getUtilisateur().getId(),
                uc.getCompetence().getId()
        );

        Utilisateur utilisateur =
                utilisateurRepository
                        .findById(
                                uc.getUtilisateur().getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Utilisateur introuvable."
                                ));

        Competence competence =
                competenceRepository
                        .findById(
                                uc.getCompetence().getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Compétence introuvable."
                                ));

        uc.setUtilisateur(utilisateur);

        uc.setCompetence(competence);

        // New declaration must be validated
        uc.setStatut(
                StatutEvaluation.EN_ATTENTE
        );

        uc.setNiveauValide(null);

        uc.setManager(null);

        uc.setDateCreation(
                LocalDateTime.now()
        );

        uc.setDateValidation(null);

        return repository.save(uc);
    }

    // =====================================================
    // UPDATE
    // Employee modifies his declaration
    // =====================================================

    public UtilisateurCompetence update(
            Long id,
            UtilisateurCompetence updated) {

        verifierNiveau(
                updated.getNiveauEmploye()
        );

        UtilisateurCompetence uc =
                getById(id);

        uc.setNiveauEmploye(
                updated.getNiveauEmploye()
        );

        // Must be validated again
        uc.setStatut(
                StatutEvaluation.EN_ATTENTE
        );

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
    // Get all his competencies
    // =====================================================

    public List<UtilisateurCompetence> getByUtilisateur(
            Long utilisateurId) {

        return repository.findByUtilisateurId(
                utilisateurId
        );
    }

    // =====================================================
    // MANAGER
    // Get pending evaluations
    // =====================================================

    public List<UtilisateurCompetence> getPending() {

        return repository.findByStatut(
                StatutEvaluation.EN_ATTENTE
        );
    }

    // =====================================================
    // MANAGER
    // Get validations made by manager
    // =====================================================

    public List<UtilisateurCompetence> getByManager(
            Long managerId) {

        return repository.findByManagerId(
                managerId
        );
    }

    // =====================================================
    // MANAGER
    // Validate employee competency
    // =====================================================

    public UtilisateurCompetence validate(
            Long id,
            Integer niveau,
            Long managerId) {

        verifierNiveau(niveau);

        UtilisateurCompetence uc =
                getById(id);

        Utilisateur manager =
                utilisateurRepository
                        .findById(managerId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Manager introuvable."
                                ));

        uc.setManager(manager);

        uc.setNiveauValide(niveau);

        uc.setStatut(
                StatutEvaluation.VALIDE
        );

        uc.setDateValidation(
                LocalDateTime.now()
        );

        return repository.save(uc);
    }

    // =====================================================
    // MANAGER
    // Pending validations DTO
    // =====================================================

    public List<PendingValidationDTO> getPendingValidations(
            Long managerId) {

        List<UtilisateurCompetence> evaluations =
                repository.findByUtilisateurManagerIdAndStatut(
                        managerId,
                        StatutEvaluation.EN_ATTENTE
                );

        List<PendingValidationDTO> result =
                new ArrayList<>();

        for (UtilisateurCompetence evaluation :
                evaluations) {

            PendingValidationDTO dto =
                    new PendingValidationDTO();

            dto.setEvaluationId(
                    evaluation.getId()
            );

            dto.setEmploye(
                    evaluation.getUtilisateur().getNom()
                            + " "
                            + evaluation.getUtilisateur().getPrenom()
            );

            dto.setCompetence(
                    evaluation.getCompetence().getNom()
            );

            dto.setNiveauPropose(
                    evaluation.getNiveauEmploye()
            );

            result.add(dto);
        }

        return result;
    }

    // =====================================================
    // PRIVATE
    // Verify level 0 -> 4
    // =====================================================

    private void verifierNiveau(Integer niveau) {

        if (niveau == null ||
                niveau < 0 ||
                niveau > 4) {

            throw new RuntimeException(
                    "Le niveau doit être compris entre 0 et 4."
            );
        }
    }

    // =====================================================
    // PRIVATE
    // Prevent duplicate competency
    // =====================================================

    private void verifierDoublon(
            Long utilisateurId,
            Long competenceId) {

        if (repository
                .findByUtilisateurIdAndCompetenceId(
                        utilisateurId,
                        competenceId
                )
                .isPresent()) {

            throw new RuntimeException(
                    "Cette compétence est déjà déclarée pour cet utilisateur."
            );
        }
    }
    public List<ManagerEvaluationDTO> getManagerEvaluations(
            Long utilisateurId) {

        // ==========================================
        // 1. Find employee
        // ==========================================

        Utilisateur utilisateur =
                utilisateurRepository.findById(utilisateurId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Utilisateur introuvable."
                                ));


        // ==========================================
        // 2. Get employee poste
        // ==========================================

        String poste = utilisateur.getPoste();

        if (poste == null || poste.isBlank()) {

            throw new RuntimeException(
                    "L'utilisateur n'a pas de poste."
            );
        }


        // ==========================================
        // 3. Get REQUIRED competencies for poste
        // ==========================================

        List<PosteCompetence> requiredCompetencies =
                posteCompetenceRepository.findByPoste(poste);


        // ==========================================
        // 4. Get ALL employee competencies
        // ==========================================

        List<UtilisateurCompetence> evaluations =
                repository.findByUtilisateurId(utilisateurId);


        // ==========================================
        // 5. Result
        // ==========================================

        List<ManagerEvaluationDTO> result =
                new ArrayList<>();


        // ==========================================
        // 6. ADD POSTE REQUIRED COMPETENCIES
        // ==========================================

        for (PosteCompetence pc : requiredCompetencies) {

            if (pc.getCompetence() == null) {
                continue;
            }

            Long competenceId =
                    pc.getCompetence().getId();


            ManagerEvaluationDTO dto =
                    new ManagerEvaluationDTO();


            dto.setCompetenceId(
                    competenceId
            );

            dto.setCompetence(
                    pc.getCompetence().getNom()
            );


            // ==========================================
            // REQUIRED LEVEL
            // ==========================================

            dto.setNiveauRequis(
                    pc.getNiveauRequis()
            );


            // ==========================================
            // Find employee association
            // ==========================================

            UtilisateurCompetence evaluation =
                    evaluations.stream()
                            .filter(e ->
                                    e.getCompetence() != null
                                            &&
                                            e.getCompetence()
                                                    .getId()
                                                    .equals(competenceId)
                            )
                            .findFirst()
                            .orElse(null);


            if (evaluation == null) {

                dto.setEvaluationId(null);

                dto.setNiveauEmploye(null);

                dto.setNiveauValide(null);

                dto.setStatut("NON_EVALUE");

            } else {

                dto.setEvaluationId(
                        evaluation.getId()
                );

                dto.setNiveauEmploye(
                        evaluation.getNiveauEmploye()
                );

                dto.setNiveauValide(
                        evaluation.getNiveauValide()
                );

                dto.setStatut(
                        evaluation.getStatut() != null
                                ? evaluation.getStatut().name()
                                : "NON_EVALUE"
                );
            }


            result.add(dto);
        }


        // ==========================================
        // 7. ADD EXTRA EMPLOYEE COMPETENCIES
        // ==========================================

        for (UtilisateurCompetence evaluation :
                evaluations) {

            if (evaluation.getCompetence() == null) {
                continue;
            }


            Long competenceId =
                    evaluation.getCompetence().getId();


            // ==========================================
            // Already added because it belongs
            // to the employee's poste
            // ==========================================

            boolean alreadyExists =
                    result.stream()
                            .anyMatch(dto ->
                                    dto.getCompetenceId()
                                            .equals(competenceId)
                            );


            if (alreadyExists) {
                continue;
            }


            // ==========================================
            // EXTRA COMPETENCE
            // ==========================================

            ManagerEvaluationDTO dto =
                    new ManagerEvaluationDTO();


            dto.setCompetenceId(
                    competenceId
            );

            dto.setCompetence(
                    evaluation.getCompetence().getNom()
            );


            // Extra competency has no required
            // level for this employee's poste

            dto.setNiveauRequis(null);


            dto.setEvaluationId(
                    evaluation.getId()
            );

            dto.setNiveauEmploye(
                    evaluation.getNiveauEmploye()
            );

            dto.setNiveauValide(
                    evaluation.getNiveauValide()
            );

            dto.setStatut(
                    evaluation.getStatut() != null
                            ? evaluation.getStatut().name()
                            : "NON_EVALUE"
            );


            result.add(dto);
        }


        return result;
    }

}
