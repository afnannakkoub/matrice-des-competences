package com.matrice.backend.repository;

import com.matrice.backend.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository
        extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Utilisateur> findByManagerId(Long managerId);

    long countByManagerId(Long managerId);

    long countByRoleNom(String roleNom);

    long count();

    @Query("""
        SELECT DISTINCT u.poste
        FROM Utilisateur u
        ORDER BY u.poste
    """)
    List<String> findDistinctPostes();
}