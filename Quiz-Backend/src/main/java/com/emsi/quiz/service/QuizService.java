package com.emsi.quiz.service;

import java.io.IOException;
import java.util.List;

import com.emsi.quiz.entity.Participation;
import com.emsi.quiz.entity.Question;
import com.emsi.quiz.entity.Quiz;
import com.google.zxing.WriterException;

public interface QuizService {
	//Quiz createQuiz(Quiz quiz, long idUtilisateur) throws WriterException, IOException, Exception;
	//avoir la liste des quiz publiques
	List<Quiz> getPublicQuiz();
	//participer à un quiz
	Participation participerQuiz(long idUtilisateur, long idQuiz);
	//avoir toutes les question d'un quiz : 
	List<Question> getQuizQuestionList(long idQuiz);
	//avoir un quiz par son id
	Quiz getQuizById(long id);
	Quiz createQuiz(Quiz quiz, long idUtilisateur, Long groupeId, List<Question> questions) throws WriterException, IOException, Exception;
	//Supprimer un quiz
	//update quiz
	//chercher un quiz par mots clés
	List<Quiz> findQuizzesByKeywords(String keyword);
	//total des quiz
	int totalQuiz();
	
	long totalQuizPubliques();
	
	long totalQuizPrives();
	//String generateShareLink(long quizId, String codeAcces);
	String generateShareLink(long quizId);


}
