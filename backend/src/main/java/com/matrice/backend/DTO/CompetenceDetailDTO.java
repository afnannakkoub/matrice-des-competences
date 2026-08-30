package com.matrice.backend.DTO;

public class CompetenceDetailDTO {

    private String competence;

    private Integer niveauRequis;

    private Integer niveauValide;

    public CompetenceDetailDTO() {
    }

    public CompetenceDetailDTO(
            String competence,
            Integer niveauRequis,
            Integer niveauValide) {

        this.competence = competence;
        this.niveauRequis = niveauRequis;
        this.niveauValide = niveauValide;
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

    public Integer getNiveauValide() {
        return niveauValide;
    }

    public void setNiveauValide(Integer niveauValide) {
        this.niveauValide = niveauValide;
    }
}