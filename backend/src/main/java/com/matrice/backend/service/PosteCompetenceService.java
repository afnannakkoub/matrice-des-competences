package com.matrice.backend.service;

import com.matrice.backend.entity.PosteCompetence;
import com.matrice.backend.repository.PosteCompetenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PosteCompetenceService {

    private final PosteCompetenceRepository repository;

    public PosteCompetenceService(PosteCompetenceRepository repository) {
        this.repository = repository;
    }

    // =========================
    // CREATE
    // =========================

    public PosteCompetence save(PosteCompetence posteCompetence) {

        return repository.save(posteCompetence);

    }

    // =========================
    // READ ALL
    // =========================

    public List<PosteCompetence> getAll() {

        return repository.findAll();

    }

    // =========================
    // READ BY ID
    // =========================

    public Optional<PosteCompetence> getById(Long id) {

        return repository.findById(id);

    }

    // =========================
    // READ BY POSITION
    // =========================

    public List<PosteCompetence> getByPoste(String poste) {

        return repository.findByPoste(poste);

    }

    // =========================
    // UPDATE
    // =========================

    public PosteCompetence update(
            Long id,
            PosteCompetence posteCompetence) {

        PosteCompetence existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("PosteCompetence introuvable"));

        existing.setPoste(posteCompetence.getPoste());

        existing.setCompetence(posteCompetence.getCompetence());

        existing.setNiveauRequis(posteCompetence.getNiveauRequis());

        return repository.save(existing);

    }

    // =========================
    // DELETE
    // =========================

    public void delete(Long id) {

        repository.deleteById(id);

    }

}