package com.example.gestion_produit.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Entity
    @Table(name = "produits")
    public class Produit {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Setter
        @Getter
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

        @Column(nullable = false, updatable = false)
        private LocalDateTime dateCreation;

        @Column(nullable = false)
        private LocalDateTime dateModification;

        // Constructeurs
       /* public Produit() {
        }*/

        /*public Produit(String nom, String description, BigDecimal prix, Integer quantiteStock) {
            this.nom = nom;
            this.description = description;
            this.prix = prix;
            this.quantiteStock = quantiteStock;
        }*/

        // Méthodes de lifecycle
        @PrePersist
        protected void onCreate() {
            dateCreation = LocalDateTime.now();
            dateModification = LocalDateTime.now();
        }

        @PreUpdate
        protected void onUpdate() {
            dateModification = LocalDateTime.now();
        }

        // Getters et Setters
        /*public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BigDecimal getPrix() {
            return prix;
        }

        public void setPrix(BigDecimal prix) {
            this.prix = prix;
        }

        public Integer getQuantiteStock() {
            return quantiteStock;
        }

        public void setQuantiteStock(Integer quantiteStock) {
            this.quantiteStock = quantiteStock;
        }

        public LocalDateTime getDateCreation() {
            return dateCreation;
        }

        public void setDateCreation(LocalDateTime dateCreation) {
            this.dateCreation = dateCreation;
        }

        public LocalDateTime getDateModification() {
            return dateModification;
        }

        public void setDateModification(LocalDateTime dateModification) {
            this.dateModification = dateModification;
        }*/
    }

