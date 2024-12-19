package com.emsi.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emsi.quiz.entity.Reponse;


@Repository
public interface ReponseRepository extends JpaRepository<Reponse, Long> {
    
}

