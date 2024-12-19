package com.emsi.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emsi.quiz.entity.Participation;
import com.emsi.quiz.entity.Quiz;
import com.emsi.quiz.entity.Utilisateur;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
	Participation findByUtilisateurAndQuiz(Utilisateur utilisateur, Quiz quiz);
	//nombre de tentatives utilisées
	int countByUtilisateurAndQuiz(Utilisateur utilisateur, Quiz quiz);
}
