package com.example.gestion_produit.repository;

import com.example.gestion_produit.models.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    // Rechercher par nom
    Optional<Produit> findByNom(String nom);
    // Recherche par nom contenant (recherche partielle)
    List<Produit> findByNomContainingIgnoreCase(String nom);
    // Recherche par prix entre deux valeurs
    List<Produit> findByPrixBetween(BigDecimal prixMin, BigDecimal prixMax);

    // Recherche produits avec stock faible
    List<Produit> findByQuantiteStockLessThan(Integer quantite);

    // Requête personnalisée avec JPQL
    @Query("SELECT p FROM Produit p WHERE p.quantiteStock > 0 ORDER BY p.nom ASC")
    List<Produit> findProduitsEnStock();

    // Compter les produits avec un prix supérieur à une valeur
    @Query("SELECT COUNT(p) FROM Produit p WHERE p.prix > :prix")
    Long countByPrixGreaterThan(@Param("prix") BigDecimal prix);
}
