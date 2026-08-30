package com.matrice.backend.DTO;

import java.util.List;
import java.util.Map;

public class PosteSkillMatrixRowDTO {

    private String poste;

    private Map<Long, Integer> niveaux;

    private Map<Long, Integer> pourcentages;

    private Map<Long, List<CompetencyScoreDTO>> details;

    public PosteSkillMatrixRowDTO() {
    }

    public PosteSkillMatrixRowDTO(
            String poste,
            Map<Long, Integer> niveaux,
            Map<Long, Integer> pourcentages,
            Map<Long, List<CompetencyScoreDTO>> details) {

        this.poste = poste;
        this.niveaux = niveaux;
        this.pourcentages = pourcentages;
        this.details = details;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Map<Long, Integer> getNiveaux() {
        return niveaux;
    }

    public void setNiveaux(Map<Long, Integer> niveaux) {
        this.niveaux = niveaux;
    }

    public Map<Long, Integer> getPourcentages() {
        return pourcentages;
    }

    public void setPourcentages(Map<Long, Integer> pourcentages) {
        this.pourcentages = pourcentages;
    }

    public Map<Long, List<CompetencyScoreDTO>> getDetails() {
        return details;
    }

    public void setDetails(
            Map<Long, List<CompetencyScoreDTO>> details) {

        this.details = details;
    }
}