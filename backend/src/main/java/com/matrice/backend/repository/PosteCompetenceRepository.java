package com.matrice.backend.repository;

import com.matrice.backend.entity.PosteCompetence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosteCompetenceRepository
        extends JpaRepository<PosteCompetence, Long> {

    List<PosteCompetence> findByPoste(String poste);

}