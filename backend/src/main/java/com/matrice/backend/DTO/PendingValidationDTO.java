package com.matrice.backend.DTO;

public class PendingValidationDTO {

    private Long evaluationId;

    private String employe;

    private String competence;

    private Integer niveauPropose;

    public PendingValidationDTO() {
    }

    public PendingValidationDTO(Long evaluationId,
                                String employe,
                                String competence,
                                Integer niveauPropose) {
        this.evaluationId = evaluationId;
        this.employe = employe;
        this.competence = competence;
        this.niveauPropose = niveauPropose;
    }

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public String getEmploye() {
        return employe;
    }

    public void setEmploye(String employe) {
        this.employe = employe;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public Integer getNiveauPropose() {
        return niveauPropose;
    }

    public void setNiveauPropose(Integer niveauPropose) {
        this.niveauPropose = niveauPropose;
    }
}
