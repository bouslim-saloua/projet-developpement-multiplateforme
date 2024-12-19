package com.emsi.quiz.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionDTO {
    private String texte;
    private String type;
    private String bonneReponse;
    private String niveauDifficulte;
    private Integer score;

   
}
