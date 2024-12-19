package com.emsi.quiz.repository;

import com.emsi.quiz.entity.Groupe;
import com.emsi.quiz.entity.Utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {

    // Trouver les groupes publics (visible = true)
    List<Groupe> findByVisible(boolean visible);

    // Rechercher des groupes par nom (ignorer la casse)
    List<Groupe> findByNomContainingIgnoreCase(String nom);

    // Trouver un groupe par son code d'accès
    Optional<Groupe> findByCodeAcces(String codeAcces);

    // Compter le total des groupes visibles
    long countByVisible(boolean visible);
    
    //trouver tous les groupes par propriétaire
    List<Groupe> findByOwnerId(Long ownerId);
}
