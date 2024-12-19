package com.emsi.quiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsi.quiz.entity.Question;
import com.emsi.quiz.entity.Quiz;
import com.emsi.quiz.repository.QuestionRepository;
import com.emsi.quiz.repository.QuizRepository;
import com.emsi.quiz.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private QuizRepository quizRepository;
	@Override
	public Question ajouterQuestion(Question question, long idQuiz) {
		Quiz quizFromDB = quizRepository.findById(idQuiz).orElseThrow(() ->new RuntimeException("Quiz not found!"));
		question.setQuiz(quizFromDB);
		return questionRepository.save(question);
	}
	

}
