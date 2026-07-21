package com.matrice.backend.controller;


import com.matrice.backend.entity.Categorie;
import com.matrice.backend.service.CategorieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {
    private final CategorieService service;

    public CategorieController(CategorieService service) {
        this.service = service;
    }

    @GetMapping
    public List<Categorie> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Categorie getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Categorie create(@RequestBody Categorie categorie) {
        return service.save(categorie);
    }

    @PutMapping("/{id}")
    public Categorie update(@PathVariable Long id,
                            @RequestBody Categorie categorie) {
        return service.update(id, categorie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
