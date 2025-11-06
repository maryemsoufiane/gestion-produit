package com.example.gestion_produit.services;

import com.example.gestion_produit.models.Produit;
import com.example.gestion_produit.repository.ProduitRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProduitService {
    private ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }
    // CREATE - Créer un nouveau produit
    public Produit creerProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    // READ - Récupérer tous les produits
    public List<Produit> obtenirTousLesProduits() {
        return produitRepository.findAll();
    }

    // READ - Récupérer un produit par ID
    public Optional<Produit> obtenirProduitParId(Long id) {
        return produitRepository.findById(id);
    }

    // READ - Rechercher des produits par nom
    public List<Produit> rechercherParNom(String nom) {
        return produitRepository.findByNomContainingIgnoreCase(nom);
    }

    // READ - Récupérer les produits en stock
    public List<Produit> obtenirProduitsEnStock() {
        return produitRepository.findProduitsEnStock();
    }

    // UPDATE - Mettre à jour un produit
    public Produit mettreAJourProduit(Long id, Produit produitDetails) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'id: " + id));

        produit.setNom(produitDetails.getNom());
        produit.setDescription(produitDetails.getDescription());
        produit.setPrix(produitDetails.getPrix());
        produit.setQuantiteStock(produitDetails.getQuantiteStock());

        return produitRepository.save(produit);
    }

    // UPDATE - Mettre à jour le stock
    public Produit mettreAJourStock(Long id, Integer nouvelleQuantite) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'id: " + id));

        produit.setQuantiteStock(nouvelleQuantite);
        return produitRepository.save(produit);
    }

    // DELETE - Supprimer un produit par ID
    public void supprimerProduit(Long id) {
        if (!produitRepository.existsById(id)) {
            throw new RuntimeException("Produit non trouvé avec l'id: " + id);
        }
        produitRepository.deleteById(id);
    }

    // DELETE - Supprimer tous les produits
    public void supprimerTousLesProduits() {
        produitRepository.deleteAll();
    }

    // Vérifier si un produit existe
    public boolean produitExiste(Long id) {
        return produitRepository.existsById(id);
    }
}
