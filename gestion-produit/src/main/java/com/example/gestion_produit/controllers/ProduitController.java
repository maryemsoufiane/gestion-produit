package com.example.gestion_produit.controllers;

import com.example.gestion_produit.models.Produit;
import com.example.gestion_produit.services.ProduitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "*")
public class ProduitController {
    private final ProduitService produitService;

    @Autowired
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    // CREATE - Créer un nouveau produit
    @PostMapping
    public ResponseEntity<Produit> creerProduit(@Valid @RequestBody Produit produit) {
        Produit nouveauProduit = produitService.creerProduit(produit);
        return new ResponseEntity<>(nouveauProduit, HttpStatus.CREATED);
    }
    // READ - Récupérer tous les produits
    @GetMapping
    public ResponseEntity<List<Produit>> obtenirTousLesProduits() {
        List<Produit> produits = produitService.obtenirTousLesProduits();
        return ResponseEntity.ok(produits);
    }

    // READ - Récupérer un produit par ID
    @GetMapping("/{id}")
    public ResponseEntity<Produit> obtenirProduitParId(@PathVariable Long id) {
        return produitService.obtenirProduitParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // READ - Rechercher des produits par nom
    @GetMapping("/recherche")
    public ResponseEntity<List<Produit>> rechercherProduits(@RequestParam String nom) {
        List<Produit> produits = produitService.rechercherParNom(nom);
        return ResponseEntity.ok(produits);
    }

    // READ - Récupérer les produits en stock
    @GetMapping("/en-stock")
    public ResponseEntity<List<Produit>> obtenirProduitsEnStock() {
        List<Produit> produits = produitService.obtenirProduitsEnStock();
        return ResponseEntity.ok(produits);
    }

    // UPDATE - Mettre à jour un produit complet
    @PutMapping("/{id}")
    public ResponseEntity<Produit> mettreAJourProduit(
            @PathVariable Long id,
            @Valid @RequestBody Produit produit) {
        try {
            Produit produitMisAJour = produitService.mettreAJourProduit(id, produit);
            return ResponseEntity.ok(produitMisAJour);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE - Mettre à jour uniquement le stock (PATCH)
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Produit> mettreAJourStock(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> update) {
        try {
            Integer nouvelleQuantite = update.get("quantiteStock");
            Produit produitMisAJour = produitService.mettreAJourStock(id, nouvelleQuantite);
            return ResponseEntity.ok(produitMisAJour);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - Supprimer un produit par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProduit(@PathVariable Long id) {
        try {
            produitService.supprimerProduit(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - Supprimer tous les produits
    @DeleteMapping
    public ResponseEntity<Void> supprimerTousLesProduits() {
        produitService.supprimerTousLesProduits();
        return ResponseEntity.noContent().build();
    }

    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API Produits fonctionne correctement!");
    }
}
