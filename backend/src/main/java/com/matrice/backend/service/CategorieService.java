package com.matrice.backend.service;

import com.matrice.backend.entity.Categorie;
import com.matrice.backend.repository.CategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {
    private final CategorieRepository repository;

    public CategorieService(CategorieRepository repository) {
        this.repository = repository;
    }

    public List<Categorie> getAll() {
        return repository.findAll();
    }

    public Categorie getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categorie introuvable"));
    }

    public Categorie save(Categorie categorie) {

        if(repository.existsByNom(categorie.getNom()))
            throw new RuntimeException("Cette catégorie existe déjà.");

        return repository.save(categorie);
    }

    public Categorie update(Long id, Categorie categorie) {

        Categorie c = getById(id);

        c.setNom(categorie.getNom());
        c.setDescription(categorie.getDescription());


        return repository.save(c);
    }

    public void delete(Long id) {

        repository.deleteById(id);

    }
}
