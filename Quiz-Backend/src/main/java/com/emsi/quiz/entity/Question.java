package com.emsi.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String texte;
    private String type;
    private String bonneReponse;
    private String niveauDifficulte;
    private int score;
    
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Reponse> reponses = new ArrayList<>();

    public Question(String texte, String type, String bonneReponse, String niveauDifficulte, int score) {
        this.texte = texte;
        this.type = type;
        this.bonneReponse = bonneReponse;
        this.niveauDifficulte = niveauDifficulte;
        this.score = score;
    }
    
    }
