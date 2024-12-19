package com.emsi.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emsi.quiz.entity.Quiz;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
	List<Quiz> findByEstPublicTrue();
	//find quizzes using keyword
	 	@Query("SELECT q FROM Quiz q WHERE " +
	           "LOWER(q.titre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(q.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(q.sujet) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	    List<Quiz> searchQuizzesByKeyword(@Param("keyword") String keyword);
	 	
	 	//total des quiz privés
	 	long countByEstPublicFalse();
	 	//total des quiz publiques
	 	long countByEstPublicTrue();
}

