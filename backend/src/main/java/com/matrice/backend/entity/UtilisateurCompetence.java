package com.matrice.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "utilisateur_competence",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "utilisateur_id",
                        "competence_id"
                })
        }
)
public class UtilisateurCompetence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // Employee
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    // ==========================
    // Skill
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competence_id", nullable = false)
    private Competence competence;

    // ==========================
    // Employee proposed level (0 -> 4)
    // ==========================

    @Column(name = "niveau_propose", nullable = false)
    private Integer niveauPropose;

    // ==========================
    // Manager validated level (0 -> 4)
    // ==========================

    @Column(name = "niveau_valide")
    private Integer niveauValide;

    // ==========================
    // Evaluation status
    // ==========================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutEvaluation statut;

    // ==========================
    // Dates
    // ==========================

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_validation")
    private LocalDateTime dateValidation;

    // ==========================
    // Manager who validated
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Utilisateur manager;

    // =====================================================
    // Constructors
    // =====================================================

    public UtilisateurCompetence() {
        this.dateCreation = LocalDateTime.now();
        this.statut = StatutEvaluation.EN_ATTENTE;
    }

    public UtilisateurCompetence(Long id,
                                 Utilisateur utilisateur,
                                 Competence competence,
                                 Integer niveauPropose,
                                 Integer niveauValide,
                                 StatutEvaluation statut,
                                 LocalDateTime dateCreation,
                                 LocalDateTime dateValidation,
                                 Utilisateur manager) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.competence = competence;
        this.niveauPropose = niveauPropose;
        this.niveauValide = niveauValide;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateValidation = dateValidation;
        this.manager = manager;
    }

    // =====================================================
    // Getters & Setters
    // =====================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    public Integer getNiveauPropose() {
        return niveauPropose;
    }

    public void setNiveauPropose(Integer niveauPropose) {
        this.niveauPropose = niveauPropose;
    }

    public Integer getNiveauValide() {
        return niveauValide;
    }

    public void setNiveauValide(Integer niveauValide) {
        this.niveauValide = niveauValide;
    }

    public StatutEvaluation getStatut() {
        return statut;
    }

    public void setStatut(StatutEvaluation statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(LocalDateTime dateValidation) {
        this.dateValidation = dateValidation;
    }

    public Utilisateur getManager() {
        return manager;
    }

    public void setManager(Utilisateur manager) {
        this.manager = manager;
    }
}