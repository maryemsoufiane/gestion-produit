package com.example.gestion_produit.repository;

import com.example.gestion_produit.models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategorieRepository extends JpaRepository<Categorie,Integer> {
    @Override
    Optional<Categorie> findById(Integer integer);
    // Vérifier si une catégorie existe par nom
    boolean existsByNom(String nom);

    // Compter le nombre de produits dans une catégorie
    @Query("SELECT COUNT(p) FROM Produit p WHERE p.categorie.id = :categorieId")
    Long countProduitsInCategorie(Long categorieId);
}
