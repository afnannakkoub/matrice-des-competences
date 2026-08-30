package com.matrice.backend.DTO;

public class EmployeeSkillMatrixDTO {

    private Long competenceId;

    private String competence;

    private Integer niveauRequis;

    private Integer niveauActuel;

    private Integer niveauEvaluation;

    private Integer gap;

    private String statut;
    private Long evaluationId;

    public EmployeeSkillMatrixDTO() {
    }

    public EmployeeSkillMatrixDTO(
            Long competenceId,
            String competence,
            Integer niveauRequis,
            Integer niveauActuel,
            Integer niveauEvaluation,
            Integer gap,
            String statut) {

        this.competenceId = competenceId;
        this.competence = competence;
        this.niveauRequis = niveauRequis;
        this.niveauActuel = niveauActuel;
        this.niveauEvaluation = niveauEvaluation;
        this.gap = gap;
        this.statut = statut;
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

    public Integer getNiveauActuel() {
        return niveauActuel;
    }

    public void setNiveauActuel(Integer niveauActuel) {
        this.niveauActuel = niveauActuel;
    }

    public Integer getNiveauEvaluation() {
        return niveauEvaluation;
    }

    public void setNiveauEvaluation(Integer niveauEvaluation) {
        this.niveauEvaluation = niveauEvaluation;
    }

    public Integer getGap() {
        return gap;
    }

    public void setGap(Integer gap) {
        this.gap = gap;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }
}