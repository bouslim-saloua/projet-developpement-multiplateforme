package com.emsi.quiz.dto;

import java.util.Set;

import com.emsi.quiz.entity.Utilisateur;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GroupeDto {
		private Long id;
	    private String nom;
	    private String description;
	    private String codeAcces;
	    private boolean visible;
		public GroupeDto(Long id, String nom, String description, String codeAcces, boolean visible
				) {
			super();
			this.id = id;
			this.nom = nom;
			this.description = description;
			this.codeAcces = codeAcces;
			this.visible = visible;
			
		}
		public GroupeDto() {
			super();
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
		
	    
	    
}
