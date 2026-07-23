package com.matrice.backend.DTO;

public class ManagerDashboardDTO {
    private String manager;

    private Long nombreEmployes;

    private Long evaluationsEnAttente;

    private Long competencesValidees;

    public ManagerDashboardDTO() {
    }

    public ManagerDashboardDTO(String manager,
                               Long nombreEmployes,
                               Long evaluationsEnAttente,
                               Long competencesValidees) {

        this.manager = manager;
        this.nombreEmployes = nombreEmployes;
        this.evaluationsEnAttente = evaluationsEnAttente;
        this.competencesValidees = competencesValidees;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Long getNombreEmployes() {
        return nombreEmployes;
    }

    public void setNombreEmployes(Long nombreEmployes) {
        this.nombreEmployes = nombreEmployes;
    }

    public Long getEvaluationsEnAttente() {
        return evaluationsEnAttente;
    }

    public void setEvaluationsEnAttente(Long evaluationsEnAttente) {
        this.evaluationsEnAttente = evaluationsEnAttente;
    }

    public Long getCompetencesValidees() {
        return competencesValidees;
    }

    public void setCompetencesValidees(Long competencesValidees) {
        this.competencesValidees = competencesValidees;
    }
}
