package com.matrice.backend.repository;

import com.matrice.backend.entity.PosteCompetence;
import com.matrice.backend.entity.StatutEvaluation;
import com.matrice.backend.entity.UtilisateurCompetence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UtilisateurCompetenceRepository
        extends JpaRepository<UtilisateurCompetence, Long>,
        JpaSpecificationExecutor<UtilisateurCompetence> {

    // =====================================================
    // EMPLOYEE
    // =====================================================

    // All skills of one employee
    List<UtilisateurCompetence> findByUtilisateurId(Long utilisateurId);

    // One specific skill of one employee
    Optional<UtilisateurCompetence> findByUtilisateurIdAndCompetenceId(
            Long utilisateurId,
            Long competenceId
    );

    // Employee skills by status
    List<UtilisateurCompetence> findByUtilisateurIdAndStatut(
            Long utilisateurId,
            StatutEvaluation statut
    );

    // =====================================================
    // MANAGER
    // =====================================================

    // Skills validated by a manager
    List<UtilisateurCompetence> findByManagerId(Long managerId);

    // All skills of the manager's team
    List<UtilisateurCompetence> findByUtilisateurManagerId(Long managerId);

    // Waiting validations of the manager's team
    List<UtilisateurCompetence> findByUtilisateurManagerIdAndStatut(
            Long managerId,
            StatutEvaluation statut
    );

    // =====================================================
    // ADMIN
    // =====================================================

    // All evaluations having a specific status
    List<UtilisateurCompetence> findByStatut(StatutEvaluation statut);

    // Find all evaluations for one skill
    List<UtilisateurCompetence> findByCompetenceId(Long competenceId);
    long countByUtilisateurManagerIdAndStatut(
            Long managerId,
            StatutEvaluation statut
    );

    // =====================================================
    // DASHBOARD / REPORTS
    // =====================================================

    long countByStatut(StatutEvaluation statut);

    long countByUtilisateurId(Long utilisateurId);

    long countByManagerId(Long managerId);

    long countByCompetenceId(Long competenceId);

    public interface PosteCompetenceRepository
            extends JpaRepository<PosteCompetence, Long> {

        List<PosteCompetence> findByPoste(String poste);
    }
}