package com.example.gestion_produit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "categories")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "le nom de la catégorie est obligatoire")
    @Column(nullable = false, unique = true)
    private String nom;

    @Size(max=255 , message = " La description ne doit pas dépasser 255 caractéres ")
    @Column(length = 255)
    private String description;

    /**
     * Relation One-to-Many avec Produit
     * - Une Catégorie peut avoir PLUSIEURS produits
     * - mappedBy = "categorie" : indique que c'est Produit qui possède la FK
     * - cascade = CascadeType.ALL : les opérations se propagent aux produits
     * - orphanRemoval = true : si on retire un produit de la liste, il est supprimé de la BD
     * - @JsonIgnore : évite la boucle infinie lors de la sérialisation JSON
     */
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // Important : évite les références circulaires
    private List<Produit> produits = new ArrayList<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false)
    private LocalDateTime dateModification;

    // Constructeur personnalisé
    public Categorie(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    // Méthodes helper pour gérer la relation bidirectionnelle
    public void ajouterProduit(Produit produit) {
        produits.add(produit);
        produit.setCategorie(this);
    }

    public void retirerProduit(Produit produit) {
        produits.remove(produit);
        produit.setCategorie(null);
    }

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }

}
