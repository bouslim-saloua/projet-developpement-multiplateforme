package com.emsi.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Groupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private String codeAcces;
    private boolean visible;

    
    
    
    @ManyToMany
    @JoinTable(
        name = "utilisateur_groupe", 
        joinColumns = @JoinColumn(name = "groupe_id"), 
        inverseJoinColumns = @JoinColumn(name = "utilisateur_id"))
    @JsonIgnore // Ignore la sérialisation des utilisateurs dans un groupe pour éviter les boucles
    private Set<Utilisateur> utilisateurs = new HashSet<>();
    
    @OneToMany(mappedBy = "groupe", fetch = FetchType.LAZY) // LAZY par défaut
    @JsonIgnore // Facultatif, sauf si nécessaire pour éviter les boucles
    private List<Quiz> quizzes = new ArrayList<>();
 
    @ManyToOne(fetch = FetchType.LAZY) // LAZY pour éviter les problèmes de surcharge
    @JoinColumn(name = "owner_id")
    @JsonBackReference // Cette propriété est la référence inverse
    private Utilisateur owner;

    // Constructors
    public Groupe(Long id, String nom, String description, boolean visible,String codeAcces) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.visible = visible;
        this.codeAcces=codeAcces;
    }

    public Groupe(String nom, String description, boolean visible) {
        this.nom = nom;
        this.description = description;
        this.visible = visible;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCodeAcces() {
		return codeAcces;
	}

	public void setCodeAcces(String codeAcces) {
		this.codeAcces = codeAcces;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Set<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	public List<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	public Utilisateur getOwner() {
		return owner;
	}

	public void setOwner(Utilisateur owner) {
		this.owner = owner;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
}
