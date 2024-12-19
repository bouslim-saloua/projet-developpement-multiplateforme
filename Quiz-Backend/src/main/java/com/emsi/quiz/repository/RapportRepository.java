package com.emsi.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emsi.quiz.entity.Rapport;


@Repository
public interface RapportRepository extends JpaRepository<Rapport, Long> {
    
}
