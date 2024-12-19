package com.emsi.quiz.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emsi.quiz.entity.Groupe;
import com.emsi.quiz.entity.Participation;
import com.emsi.quiz.entity.Question;
import com.emsi.quiz.entity.Quiz;
import com.emsi.quiz.entity.Reponse;
import com.emsi.quiz.entity.Utilisateur;
import com.emsi.quiz.repository.GroupeRepository;
import com.emsi.quiz.repository.ParticipationRepository;
import com.emsi.quiz.repository.QuestionRepository;
import com.emsi.quiz.repository.QuizRepository;
import com.emsi.quiz.repository.ReponseRepository;
import com.emsi.quiz.repository.UtilisateurRepository;
import com.emsi.quiz.service.QuizService;
import com.emsi.quiz.utils.CodeGenerator;
import com.emsi.quiz.utils.QrCodeGenerator;
import com.emsi.quiz.utils.QrCodeReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

@Service
public class QuizServiceImpl implements QuizService{
	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private ParticipationRepository participationRepository;
	@Autowired
	private GroupeRepository groupeRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private ReponseRepository reponseRepository;
	
	private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/qrcodes/";
	private final String baseUrl = "http://localhost:4200/quizzes";

	/*@Override
	public Quiz createQuiz(Quiz quiz, long idUtilisateur) throws WriterException, IOException, Exception {
		  // Récupérer l'utilisateur de la base de données
	   Utilisateur utilisateurFromDB = utilisateurRepository.findById(idUtilisateur).orElse(null);
	    if (utilisateurFromDB == null) {
	        throw new Exception("User not found");
	    }
	    
	    // Vérifier que le nombre de tentatives est > 0
	    if (quiz.getNbreTentatives() <= 0) {
	        quiz.setNbreTentatives(1); 
	    }
	    
	    // Vérifier si le quiz est public
	    if (quiz.isEstPublic()) {
	        quiz.setCodeAcces(null);
	    } else {
	        String accessCode = CodeGenerator.generateAccessCode();
	        quiz.setCodeAcces(accessCode);
	    }
	    
	    // Vérifier que le code d'accès est un nombre de 8 chiffres
	    if (quiz.getCodeAcces() != null && !quiz.getCodeAcces().matches("\\d{8}")) {
	        throw new Exception("Le code d'accès doit être un nombre de 8 chiffres.");
	    }
	    
	    if(quiz.getCodeAcces() == null || !quiz.isEstPublic()) {
	    	throw new Exception("Un quiz qui n'est pas public doit avoir un code d'accès!!");
	    }

	    // Générer un code QR basé sur le code d'accès
	    String qrCodePath = QR_CODE_IMAGE_PATH + "quiz_" + quiz.getCodeAcces() + ".png";
	    QrCodeGenerator.generateQRCodeImage(quiz.getCodeAcces(), 200, 200, qrCodePath);
	    quiz.setQrCode(qrCodePath);
	    
	    quiz.setDateCreation(LocalDateTime.now());
	    
	    quiz.setOwner(utilisateurFromDB);
	    
	    return quizRepository.save(quiz);
		
	}*/
	@Override
	public Quiz createQuiz(Quiz quiz, long idUtilisateur, Long groupeId, List<Question> questions) throws WriterException, IOException, Exception {
	    Utilisateur utilisateurFromDB = utilisateurRepository.findById(idUtilisateur).orElse(null);
	    //System.out.println("liste des groupes : " + groupeIds);
	    if (utilisateurFromDB == null) {
	        throw new Exception("Utilisateur introuvable");
	    }

	    if (quiz.getNbreTentatives() <= 0) {
	        quiz.setNbreTentatives(1);
	    }

	    if (quiz.isEstPublic()) {
	        quiz.setCodeAcces(null);
	    } else {
	        String accessCode = CodeGenerator.generateAccessCode();
	        quiz.setCodeAcces(accessCode);
	    }

	    if (quiz.getCodeAcces() != null && !quiz.getCodeAcces().matches("\\d{8}")) {
	        throw new Exception("Le code d'accès doit être un nombre de 8 chiffres.");
	    }

	    if (quiz.getCodeAcces() == null && !quiz.isEstPublic()) {
	        throw new Exception("Un quiz qui n'est pas public doit avoir un code d'accès !");
	    }
	    String qrCodePath = QR_CODE_IMAGE_PATH + "quiz_" + quiz.getCodeAcces() + ".png";
	    QrCodeGenerator.generateQRCodeImage(quiz.getCodeAcces(), 200, 200, qrCodePath);
	    quiz.setQrCode(qrCodePath);

	    quiz.setDateCreation(LocalDateTime.now());
	    quiz.setUtilisateur(utilisateurFromDB);

	    Groupe groupe = groupeRepository.findById(groupeId).orElseThrow(() -> new  Exception("groupe not found"));
	    quiz.setGroupe(groupe);
	    Quiz savedQuiz = quizRepository.save(quiz);
	    for (Question q : questions) {
	    	q.setQuiz(savedQuiz);
	    	Question savedQuestion = questionRepository.save(q);
	    	for(Reponse reponse : q.getReponses()) {
	    		reponse.setQuestion(savedQuestion);
	    		reponseRepository.save(reponse);
	    	}
	    }
	    return savedQuiz;
	}

	@Override
	public List<Quiz> getPublicQuiz() {
		return quizRepository.findByEstPublicTrue();
	}

	@Override
	public Participation participerQuiz(long idUtilisateur, long idQuiz) {
		Optional<Utilisateur> utilisateurFromDB = utilisateurRepository.findById(idUtilisateur);
		if(!utilisateurFromDB.isPresent()) {
			throw new RuntimeException("Utilisateur non trouvé !");
		}
		Optional<Quiz> quizFromDB = quizRepository.findById(idQuiz);
		if(!quizFromDB.isPresent()) {
			throw new RuntimeException("Quiz non trouvé !");
		}
		Utilisateur utilisateur = utilisateurFromDB.get();
		Quiz quiz = quizFromDB.get();
		int nombreTentativesUtilisees = participationRepository.countByUtilisateurAndQuiz(utilisateur, quiz);
        int nbreTentativesMax = quiz.getNbreTentatives();
        if (nombreTentativesUtilisees >= nbreTentativesMax) {
            throw new RuntimeException("L'utilisateur a atteint le nombre maximum de tentatives pour ce quiz");
        }
		  	Participation participation = new Participation();
	        participation.setUtilisateur(utilisateur);
	        participation.setQuiz(quiz);
	        participation.setScore(0); 
	        participation.setTempsPris(0);
	        participation.setDateParticipation(new java.util.Date()); 

	        return participationRepository.save(participation);
	}

	@Override
	public List<Question> getQuizQuestionList(long idQuiz) {
		Quiz quizFromDB = quizRepository.findById(idQuiz).orElseThrow(() -> new RuntimeException("Quiz not found !"));
		return quizFromDB.getQuestions();
	}

	@Override
	public Quiz getQuizById(long id) {
		Optional<Quiz> quizFromDB = quizRepository.findById(id);
		return quizFromDB.get();			
	}

	@Override
	public List<Quiz> findQuizzesByKeywords(String keyword) {
		return quizRepository.searchQuizzesByKeyword(keyword);
	}
	
	@Override
	public int totalQuiz() {
		return (int) quizRepository.count();
	}


	@Override
	public long totalQuizPubliques() {
		return quizRepository.countByEstPublicTrue();
	}


	@Override
	public long totalQuizPrives() {
		return quizRepository.countByEstPublicFalse();
	}


	@Override
	public String generateShareLink(long quizId) {
		if (quizRepository.findById(quizId).isEmpty()) {
		    throw new RuntimeException("Quiz not found with ID: " + quizId);
		}
		String lien = baseUrl + "/" + quizId;
		/*if(codeAcces != null && !codeAcces.isEmpty()) {
			lien += "?code=" + codeAcces;
		}*/
		return lien;
	}
    
}
