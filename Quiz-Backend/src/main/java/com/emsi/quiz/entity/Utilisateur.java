package com.emsi.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur implements Serializable {
    public Utilisateur(String nom, String prenom, String username,String email, String password,Role role) {
		this.nom=nom;
		this.prenom=prenom;
		this.email=email;
		this.password=password;
		this.role=role;
		this.username=username;
	}

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String prenom;
    private String username;
    private String email;
    private String password;
    private String image;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "utilisateurs")
    @JsonIgnore // Ignore la sérialisation des groupes pour éviter les cycles
    private Set<Groupe> groupes = new HashSet<>();
    
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY) // LAZY par défaut
    @JsonManagedReference // Cette propriété est la référence principale
    private List<Groupe> groupesOwned = new ArrayList<>();
    
    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY) // LAZY par défaut
    @JsonIgnore // Ignore si ces propriétés ne sont pas nécessaires dans les réponses JSON
    private List<Quiz> quizzes = new ArrayList<>();
    
    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Participation> participations = new ArrayList<>();
    
    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Notification> notifications = new ArrayList<>();
    
    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Classement> classements = new ArrayList<>();
    
    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Rapport> rapports = new ArrayList<>();
}
