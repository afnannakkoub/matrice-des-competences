package com.matrice.backend.service;
import com.matrice.backend.entity.Competence;
import com.matrice.backend.repository.CompetenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetenceService {

    private final CompetenceRepository repository;

    public CompetenceService(CompetenceRepository repository) {
        this.repository = repository;
    }

    public List<Competence> getAll() {
        return repository.findAll();
    }

    public Optional<Competence> getById(Long id) {
        return repository.findById(id);
    }

    public Competence save(Competence competence) {
        return repository.save(competence);
    }

    public Competence update(Long id, Competence competence) {

        Competence c = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compétence introuvable"));

        c.setNom(competence.getNom());
        c.setDescription(competence.getDescription());
        c.setArchive(competence.getArchive());
        c.setCategorie(competence.getCategorie());

        return repository.save(c);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}