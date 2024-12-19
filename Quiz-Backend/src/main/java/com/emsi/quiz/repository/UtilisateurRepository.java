package com.emsi.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emsi.quiz.entity.Utilisateur;

import java.util.Optional;
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
	Optional<Utilisateur> findByUsername(String username);
	boolean existsByUsername(String username);
	Optional<Utilisateur> findByEmail(String email);
    boolean existsByEmail(String email);;
}
