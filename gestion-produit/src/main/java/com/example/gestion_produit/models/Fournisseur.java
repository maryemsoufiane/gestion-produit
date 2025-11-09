package com.example.gestion_produit.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entité Fournisseur
 * Relation : Un Fournisseur fournit UN SEUL Produit exclusif (One-to-One)
 * Exemple : Un fournisseur exclusif pour chaque produit
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fournisseurs")
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du fournisseur est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(nullable = false)
    private String nom;

    @Email(message = "Format d'email invalide")
    @Column(unique = true)
    private String email;

    @Size(max = 20, message = "Le téléphone ne doit pas dépasser 20 caractères")
    private String telephone;

    @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
    private String adresse;

    /**
     * Relation One-to-One avec Produit
     * - Un Fournisseur fournit UN SEUL produit
     * - mappedBy = "fournisseur" : Produit possède la FK
     * - cascade = CascadeType.ALL : les opérations se propagent
     * - @JsonIgnore : évite la référence circulaire
     */
    @OneToOne(mappedBy = "fournisseur", cascade = CascadeType.ALL)
    @JsonIgnore
    private Produit produit;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false)
    private LocalDateTime dateModification;

    // Constructeur personnalisé
    public Fournisseur(String nom, String email, String telephone, String adresse) {
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
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
