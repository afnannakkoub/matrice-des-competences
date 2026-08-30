package com.matrice.backend.DTO;

public class ValidationMatrixCellDTO {

    private Integer niveau;
    private Integer pourcentage;

    public ValidationMatrixCellDTO() {
    }

    public ValidationMatrixCellDTO(
            Integer niveau,
            Integer pourcentage) {

        this.niveau = niveau;
        this.pourcentage = pourcentage;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    public Integer getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(Integer pourcentage) {
        this.pourcentage = pourcentage;
    }
}