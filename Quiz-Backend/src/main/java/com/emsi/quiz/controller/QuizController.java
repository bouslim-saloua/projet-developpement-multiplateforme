package com.emsi.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emsi.quiz.dto.QuizRequest;
import com.emsi.quiz.entity.Quiz;
import com.emsi.quiz.service.QuizService;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
	@Autowired
	private QuizService quizService;
	
    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(@RequestBody QuizRequest quizRequest) {
        try {
            Quiz newQuiz = quizService.createQuiz(
                quizRequest.toQuiz(),
                quizRequest.getUtilisateurId(),
                quizRequest.getGroupeId(), 
                quizRequest.getQuestions()
            );
            return ResponseEntity.ok(newQuiz);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping("/api/quizzes/share")
    public String shareQuiz(@RequestParam Long quizId) {
        return quizService.generateShareLink(quizId);
    }

}
