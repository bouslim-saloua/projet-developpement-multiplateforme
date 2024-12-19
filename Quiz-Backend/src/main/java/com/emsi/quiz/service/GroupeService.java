package com.emsi.quiz.service;

import java.util.*;

import com.emsi.quiz.dto.GroupeDto;
import com.emsi.quiz.dto.UtilisateurDto;
import com.emsi.quiz.entity.Groupe;

public interface GroupeService {
	Groupe save(Groupe groupe, long idUtilisateur, List<Long> utilisateurIds) throws Exception;
	List<Groupe> findAll();
	List<Groupe> getPublicGroupes();
	List<Groupe> findByName(String nom);
	//pour accéder à un groupe privé
	boolean rejoindreGroupePrivate(String codeAccess, long idUtilisateur) throws Exception;
	//je veux avoir tout les membres d'un groupe
	List<UtilisateurDto> getAllGroupeMembers(long idGroupe);
	//je veux avoir tout les groupes qui appartient à un utilisateur donné
	List<GroupeDto> getAllGroupesByUser(long idUtilisateur);
	
	boolean rejoindreGroupePublic(long idGroupe, long idUtilisateur) throws Exception;
	
	//get groupe access code seulement if user = owner
	String getAccesCode(long idGroupe, long idUtilisateur) throws Exception;
	//supprimer un groupe by its id
	boolean supprimerGroupe(long id);
	
	
	//total groupes
	long totalGroupes();
	//total groupes publiques
	long totalGroupesPubliques();
	//total groupes privés
	long totalGroupesPrives();
}
