package com.emsi.quiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsi.quiz.entity.Question;
import com.emsi.quiz.entity.Reponse;
import com.emsi.quiz.repository.QuestionRepository;
import com.emsi.quiz.repository.ReponseRepository;
import com.emsi.quiz.service.ReponseService;

@Service
public class ReponseServiceImpl implements ReponseService{
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private ReponseRepository reponseRepository;
	
	@Override
	public Reponse ajouterReponse(long idQuestion, Reponse reponse) {
		Question questionFromDB = questionRepository.findById(idQuestion).orElseThrow(() -> new RuntimeException("Question not found!"));
		reponse.setQuestion(questionFromDB);
		return reponseRepository.save(reponse);
	}
}
