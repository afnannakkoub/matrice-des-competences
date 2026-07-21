package com.matrice.backend.repository;

import com.matrice.backend.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long>  {
    boolean existsByNom(String nom);

}
