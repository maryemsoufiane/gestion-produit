package com.example.gestion_produit.repository;

import com.example.gestion_produit.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Integer> {
    @Override
    Optional<Tag> findById(Integer integer);
}
