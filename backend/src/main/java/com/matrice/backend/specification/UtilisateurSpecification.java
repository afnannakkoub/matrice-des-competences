package com.matrice.backend.specification;
import com.matrice.backend.entity.Utilisateur;
import org.springframework.data.jpa.domain.Specification;

public class UtilisateurSpecification {

    public static Specification<Utilisateur> hasNom(String nom) {
        return (root, query, cb) ->
                nom == null || nom.isBlank()
                        ? null
                        : cb.like(cb.lower(root.get("nom")),
                        "%" + nom.toLowerCase() + "%");
    }

    public static Specification<Utilisateur> hasPrenom(String prenom) {
        return (root, query, cb) ->
                prenom == null || prenom.isBlank()
                        ? null
                        : cb.like(cb.lower(root.get("prenom")),
                        "%" + prenom.toLowerCase() + "%");
    }

    public static Specification<Utilisateur> hasEmail(String email) {
        return (root, query, cb) ->
                email == null || email.isBlank()
                        ? null
                        : cb.like(cb.lower(root.get("email")),
                        "%" + email.toLowerCase() + "%");
    }

    public static Specification<Utilisateur> hasDepartement(String departement) {
        return (root, query, cb) ->
                departement == null || departement.isBlank()
                        ? null
                        : cb.like(cb.lower(root.get("departement")),
                        "%" + departement.toLowerCase() + "%");
    }

    public static Specification<Utilisateur> hasPoste(String poste) {
        return (root, query, cb) ->
                poste == null || poste.isBlank()
                        ? null
                        : cb.like(cb.lower(root.get("poste")),
                        "%" + poste.toLowerCase() + "%");
    }

    public static Specification<Utilisateur> isActif(Boolean actif) {
        return (root, query, cb) ->
                actif == null
                        ? null
                        : cb.equal(root.get("actif"), actif);
    }

}