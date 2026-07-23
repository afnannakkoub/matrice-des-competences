package com.matrice.backend.DTO;
import java.util.Map;
public class SkillMatrixRowDTO {

    private Long utilisateurId;

    private String nom;

    private String prenom;

    private Map<String, Integer> competences;

    public SkillMatrixRowDTO() {
    }

    public SkillMatrixRowDTO(Long utilisateurId,
                             String nom,
                             String prenom,
                             Map<String, Integer> competences) {
        this.utilisateurId = utilisateurId;
        this.nom = nom;
        this.prenom = prenom;
        this.competences = competences;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Map<String, Integer> getCompetences() {
        return competences;
    }

    public void setCompetences(Map<String, Integer> competences) {
        this.competences = competences;
    }
}



