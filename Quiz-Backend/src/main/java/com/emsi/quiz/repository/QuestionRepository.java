package com.emsi.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emsi.quiz.entity.Question;



@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}

