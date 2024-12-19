package com.emsi.quiz.dto;

import java.util.List;

import com.emsi.quiz.entity.Question;
import com.emsi.quiz.entity.Quiz;

public class QuizRequest {
    private String titre;
    private String description;
    private String sujet;
    private String niveau;
    private int duree;
    private boolean estPublic;
    private String reference;
    private int nbreTentatives;
    private String type;
    private long utilisateurId;
    private Long groupeId;
    private List<Question> questions;

    public Quiz toQuiz() {
        return new Quiz(titre, description, sujet, niveau, duree, estPublic, reference, nbreTentatives, type);
    }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSujet() { return sujet; }
    public void setSujet(String sujet) { this.sujet = sujet; }
    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }
    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }
    public boolean isEstPublic() { return estPublic; }
    public void setEstPublic(boolean estPublic) { this.estPublic = estPublic; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public int getNbreTentatives() { return nbreTentatives; }
    public void setNbreTentatives(int nbreTentatives) { this.nbreTentatives = nbreTentatives; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(long utilisateurId) { this.utilisateurId = utilisateurId; }
    

	public Long getGroupeId() {
		return groupeId;
	}

	public void setGroupeId(Long groupeId) {
		this.groupeId = groupeId;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
    
     
}
