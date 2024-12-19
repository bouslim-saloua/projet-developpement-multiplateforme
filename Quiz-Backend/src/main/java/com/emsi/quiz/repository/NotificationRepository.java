package com.emsi.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emsi.quiz.entity.Notification;




public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
}
