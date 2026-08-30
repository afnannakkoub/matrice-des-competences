package com.matrice.backend.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "poste_competence")
public class PosteCompetence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String poste;

    @ManyToOne
    @JoinColumn(name = "competence_id", nullable = false)
    private Competence competence;

    @Column(name = "niveau_requis", nullable = false)
    private Integer niveauRequis;

    // =====================
    // Constructors
    // =====================

    public PosteCompetence() {
    }

    public PosteCompetence(Long id,
                           String poste,
                           Competence competence,
                           Integer niveauRequis) {

        this.id = id;
        this.poste = poste;
        this.competence = competence;
        this.niveauRequis = niveauRequis;
    }

    // =====================
    // Getters & Setters
    // =====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    public Integer getNiveauRequis() {
        return niveauRequis;
    }

    public void setNiveauRequis(Integer niveauRequis) {
        this.niveauRequis = niveauRequis;
    }
}