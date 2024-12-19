package com.emsi.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;




@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Quiz implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private String sujet;
    private String niveau;
    private int duree;
    private boolean estPublic;
    private String codeAcces;
    private String reference;
    private String qrCode;
    private int nbreTentatives;
    private String type;
    private LocalDateTime dateCreation;
    

   
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
    
    @ManyToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;
    
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();
    
    @OneToMany(mappedBy = "quiz")
    private List<Participation> participations = new ArrayList<>();
    
    @OneToMany(mappedBy = "quiz")
    private List<Notification> notifications = new ArrayList<>();
    
    @OneToMany(mappedBy = "quiz")
    private List<Classement> classements = new ArrayList<>();
    
    @OneToOne(mappedBy = "quiz", cascade = CascadeType.ALL)
    private Rapport rapport;
    public Quiz(String titre, String description, String sujet, String niveau, int duree, boolean estPublic,
                String reference, int nbreTentatives, String type) {
        this.titre = titre;
        this.description = description;
        this.sujet = sujet;
        this.niveau = niveau;
        this.duree = duree;
        this.estPublic = estPublic;
        this.reference = reference;
        this.nbreTentatives = nbreTentatives;
        this.type = type;
    }
}
