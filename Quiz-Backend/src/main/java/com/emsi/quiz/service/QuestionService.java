package com.emsi.quiz.service;

import com.emsi.quiz.entity.Question;

public interface QuestionService {
	Question ajouterQuestion(Question question, long idQuiz);
}
