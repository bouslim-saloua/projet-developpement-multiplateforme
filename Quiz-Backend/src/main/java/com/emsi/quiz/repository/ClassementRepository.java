package com.emsi.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emsi.quiz.entity.Classement;

@Repository
public interface ClassementRepository extends JpaRepository<Classement, Long> {

}
