package com.matrice.backend.DTO;

public class ManagerEvaluationDTO {

    private Long evaluationId;

    private Long competenceId;

    private String competence;

    private Integer niveauRequis;

    private Integer niveauEmploye;

    private Integer niveauValide;

    private String statut;

    public ManagerEvaluationDTO() {
    }

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Long getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(Long competenceId) {
        this.competenceId = competenceId;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public Integer getNiveauRequis() {
        return niveauRequis;
    }

    public void setNiveauRequis(Integer niveauRequis) {
        this.niveauRequis = niveauRequis;
    }

    public Integer getNiveauEmploye() {
        return niveauEmploye;
    }

    public void setNiveauEmploye(Integer niveauEmploye) {
        this.niveauEmploye = niveauEmploye;
    }

    public Integer getNiveauValide() {
        return niveauValide;
    }

    public void setNiveauValide(Integer niveauValide) {
        this.niveauValide = niveauValide;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}