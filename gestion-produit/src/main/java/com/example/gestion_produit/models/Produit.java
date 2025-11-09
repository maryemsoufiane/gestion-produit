package com.example.gestion_produit.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité Produit avec Relations :
 * - Many-to-One : Plusieurs produits → Une catégorie
 * - One-to-One : Un produit → Un fournisseur
 * - Many-to-Many : Plusieurs produits ↔ Plusieurs tags
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "produits", indexes = {
        @Index(name = "idx_nom", columnList = "nom"),
        @Index(name = "idx_prix", columnList = "prix")
})
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 100 caractères")
    @Column(nullable = false)
    private String nom;

    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    @Column(length = 500)
    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    @Column(nullable = false)
    private BigDecimal prix;

    @Min(value = 0, message = "La quantité ne peut pas être négative")
    @Column(nullable = false)
    private Integer quantiteStock = 0;

    /**
     * Relation Many-to-One avec Catégorie
     * - PLUSIEURS produits appartiennent à UNE catégorie
     * - @JoinColumn : crée la colonne FK "categorie_id" dans la table produits
     * - FetchType.LAZY : la catégorie n'est chargée que si on y accède (optimisation)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    /**
     * Relation One-to-One avec Fournisseur
     * - UN produit a UN fournisseur exclusif
     * - @JoinColumn : crée la colonne FK "fournisseur_id"
     * - cascade = PERSIST, MERGE : sauvegarde/met à jour le fournisseur automatiquement
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "fournisseur_id", unique = true)
    private Fournisseur fournisseur;

    /**
     * Relation Many-to-Many avec Tag
     * - UN produit peut avoir PLUSIEURS tags
     * - UN tag peut être sur PLUSIEURS produits
     * - @JoinTable : crée une table de jointure "produit_tags"
     *   - joinColumns : FK vers Produit (produit_id)
     *   - inverseJoinColumns : FK vers Tag (tag_id)
     * - cascade = PERSIST, MERGE : sauvegarde automatiquement les nouveaux tags
     * - Set au lieu de List pour éviter les doublons
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "produit_tags",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false)
    private LocalDateTime dateModification;

    // Constructeur personnalisé (sans relations)
    public Produit(String nom, String description, BigDecimal prix, Integer quantiteStock) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.quantiteStock = quantiteStock;
    }

    // Méthodes helper pour gérer la relation Many-to-Many avec Tag
    public void ajouterTag(Tag tag) {
        this.tags.add(tag);
        tag.getProduits().add(this);
    }

    public void retirerTag(Tag tag) {
        this.tags.remove(tag);
        tag.getProduits().remove(this);
    }

    // Méthodes de lifecycle JPA
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