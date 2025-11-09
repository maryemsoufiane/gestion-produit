package com.example.gestion_produit.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité Tag
 * Relation : Un Tag peut être appliqué à PLUSIEURS Produits,
 *            et un Produit peut avoir PLUSIEURS Tags (Many-to-Many)
 * Exemples : "Promo", "Nouveau", "Bestseller", "Eco-friendly"
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du tag est obligatoire")
    @Size(min = 2, max = 30, message = "Le nom doit contenir entre 2 et 30 caractères")
    @Column(nullable = false, unique = true)
    private String nom;

    @Size(max = 7, message = "La couleur doit être au format #RRGGBB")
    @Column(length = 7)
    private String couleur;  // Format: #FF5733

    /**
     * Relation Many-to-Many avec Produit
     * - Un Tag peut être sur PLUSIEURS produits
     * - Un Produit peut avoir PLUSIEURS tags
     * - mappedBy = "tags" : Produit gère la relation
     * - @JsonIgnore : évite la référence circulaire
     *
     * Note : On utilise Set au lieu de List pour éviter les doublons
     */
    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Produit> produits = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false)
    private LocalDateTime dateModification;

    // Constructeur personnalisé
    public Tag(String nom, String couleur) {
        this.nom = nom;
        this.couleur = couleur;
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