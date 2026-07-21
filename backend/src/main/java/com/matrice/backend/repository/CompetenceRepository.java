package com.matrice.backend.repository;

import com.matrice.backend.entity.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompetenceRepository extends
        JpaRepository<Competence, Long>,
        JpaSpecificationExecutor<Competence> {

    boolean existsByNom(String nom);

}