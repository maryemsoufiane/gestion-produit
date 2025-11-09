package com.example.gestion_produit.repository;

import com.example.gestion_produit.models.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseur,Integer> {
    @Override
    Optional<Fournisseur> findById(Integer integer);
}
