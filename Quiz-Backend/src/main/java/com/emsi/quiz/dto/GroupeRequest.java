package com.emsi.quiz.dto;

import java.util.List;

import com.emsi.quiz.entity.Groupe;


public class GroupeRequest {
	 	private String nom;
	    private String description;
	    private boolean visible;
	    private String codeAcces;
	    private long ownerId; // owner du groupe
	    private List<Long> memberIds; //membres du groupe
	    
	    public Groupe toGroupe() {
	    	return new Groupe(nom, description, visible);
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
		public boolean isVisible() {
			return visible;
		}
		public void setVisible(boolean visible) {
			this.visible = visible;
		}
		public List<Long> getMemberIds() {
			return memberIds;
		}
		public void setMemberIds(List<Long> memberIds) {
			this.memberIds = memberIds;
		}
		public long getOwnerId() {
			return ownerId;
		}
		public void setOwnerId(long ownerId) {
			this.ownerId = ownerId;
		}
		public String getCodeAcces() {
			return codeAcces;
		}
		public void setCodeAcces(String codeAcces) {
			this.codeAcces = codeAcces;
		}
	    
	    
}
