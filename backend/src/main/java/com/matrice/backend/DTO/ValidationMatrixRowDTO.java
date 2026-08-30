package com.matrice.backend.DTO;

import java.util.Map;

public class ValidationMatrixRowDTO {

    private String poste;

    private Map<Long, ValidationMatrixCellDTO> employes;

    public ValidationMatrixRowDTO() {
    }

    public ValidationMatrixRowDTO(
            String poste,
            Map<Long, ValidationMatrixCellDTO> employes) {

        this.poste = poste;
        this.employes = employes;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Map<Long, ValidationMatrixCellDTO> getEmployes() {
        return employes;
    }

    public void setEmployes(
            Map<Long, ValidationMatrixCellDTO> employes) {

        this.employes = employes;
    }
}