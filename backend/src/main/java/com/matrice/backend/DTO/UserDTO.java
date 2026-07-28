package com.matrice.backend.DTO;

public class UserDTO {
    private Long id;

    private String nom;

    private String prenom;

    private String email;

    private String poste;

    private String departement;

    private Boolean actif;

    private String role;

    private String manager;

    public UserDTO() {
    }

    public UserDTO(Long id,
                   String nom,
                   String prenom,
                   String email,
                   String poste,
                   String departement,
                   Boolean actif,
                   String role,
                   String manager) {

        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.poste = poste;
        this.departement = departement;
        this.actif = actif;
        this.role = role;
        this.manager = manager;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }


}
