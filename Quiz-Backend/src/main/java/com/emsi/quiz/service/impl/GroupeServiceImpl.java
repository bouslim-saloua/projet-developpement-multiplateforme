package com.emsi.quiz.service.impl;

import com.emsi.quiz.dto.GroupeDto;
import com.emsi.quiz.dto.GroupeRequest;
import com.emsi.quiz.dto.UtilisateurDto;
import com.emsi.quiz.entity.Groupe;
import com.emsi.quiz.entity.Utilisateur;
import com.emsi.quiz.repository.GroupeRepository;
import com.emsi.quiz.repository.UtilisateurRepository;
import com.emsi.quiz.service.GroupeService;

import jakarta.persistence.EntityNotFoundException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupeServiceImpl implements GroupeService {

    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
	public Groupe save(Groupe groupe, long idUtilisateur, List<Long> utilisateurIds) throws Exception {
		//Groupe groupeFromDB = groupeRepository.findById(groupe.getId()).orElse(null);
		//System.out.println("Liste utilisateur : " + utilisateurIds);
		Utilisateur utilisateurFromDB = utilisateurRepository.findById(idUtilisateur).orElse(null);
		if (utilisateurFromDB == null) {
			throw new Exception("Utilisateur with id : " + idUtilisateur + " not found.");
		} /*else if (groupeFromDB != null) {
			throw new Exception("Groupe with id : " + groupe.getId() + " already exists");
		}*/
		// Génération d'un code alphanumeric aléatoire de 8 caractères
		String randomAccessCode;
		Groupe groupeRandomCheck;
		//si le groupe est publique set its code access to null
		if(groupe.isVisible()) {
			groupe.setCodeAcces(null);
		}else {
		do {
		    randomAccessCode = RandomStringUtils.randomAlphanumeric(8);
		    groupeRandomCheck = groupeRepository.findByCodeAcces(randomAccessCode).orElse(null);
		} while (groupeRandomCheck != null);
		groupe.setCodeAcces(randomAccessCode);
		}
		// set the groupe owner
		groupe.setOwner(utilisateurFromDB);
		//ajouter l'owner aux membres du groupe
		 if (utilisateurIds != null && !utilisateurIds.isEmpty()) {
		        // Vérifier si la liste contient uniquement des 0
		        boolean onlyZeros = utilisateurIds.stream().allMatch(id -> id == 0);
		        if (onlyZeros) {
		        	groupe.setUtilisateurs(new HashSet<>());
		        }

		        Set<Utilisateur> utilisateurs = new HashSet<>(utilisateurRepository.findAllById(utilisateurIds));

		        if (utilisateurs.isEmpty()) {
		            //throw new Exception("Aucun utilisateur valide trouvé pour les IDs fournis.");
		        	groupe.setUtilisateurs(new HashSet<>());
		        }
		        groupe.setUtilisateurs(utilisateurs);
		    } else {
		        groupe.setUtilisateurs(new HashSet<>());
		    }
		groupe.getUtilisateurs().add(utilisateurFromDB);
		return groupeRepository.save(groupe);
	}

    @Override
    public List<Groupe> findAll() {
        return groupeRepository.findAll();
    }

    @Override
    public List<Groupe> getPublicGroupes() {
        return groupeRepository.findByVisible(true);
    }

    @Override
    public List<Groupe> findByName(String nom) {
        return groupeRepository.findByNomContainingIgnoreCase(nom);
    }

    @Override
    public boolean rejoindreGroupePrivate(String codeAccess, long idUtilisateur) throws Exception {
        Groupe groupe = groupeRepository.findByCodeAcces(codeAccess)
                .orElseThrow(() -> new Exception("Code d'accès invalide"));
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur)
                .orElseThrow(() -> new Exception("Utilisateur non trouvé"));

        if (!groupe.isVisible()) {
            groupe.getUtilisateurs().add(utilisateur);
            groupeRepository.save(groupe);
            return true;
        }
        throw new Exception("Groupe non privé");
    }

    @Override
    public	List<UtilisateurDto> getAllGroupeMembers(long idGroupe) {
    	Groupe groupe = groupeRepository.findById(idGroupe)
                .orElseThrow(() -> new EntityNotFoundException("Groupe with ID " + idGroupe + " not found"));

        return groupe.getUtilisateurs().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupeDto> getAllGroupesByUser(long idUtilisateur) {
        
            /*    return utilisateur.getGroupesOwned().stream()
                		.map(this::convertGroupeToDto)
                .collect(Collectors.toList());*/
    	List<Groupe> groupes = groupeRepository.findByOwnerId(idUtilisateur);
    	return groupes.stream().map(this::convertGroupeToDto)
    			.collect(Collectors.toList());
    }

    @Override
	public boolean rejoindreGroupePublic(long idGroupe , long idUtilisateur) throws Exception {
		Groupe groupeFromDB = groupeRepository.findById(idGroupe).orElse(null);
		if(groupeFromDB == null) {
			throw new Exception("Groupe Not found");
		}
		
		Utilisateur utilisateurFromDB = utilisateurRepository.findById(idUtilisateur).orElse(null);
		if (utilisateurFromDB == null) throw new Exception("Utilisateur not found");
		//vérifier si l'utilisateur est déjà membre du groupe
		if(groupeFromDB.getUtilisateurs().contains(utilisateurFromDB)) {
			return false;
		}
		
		groupeFromDB.getUtilisateurs().add(utilisateurFromDB);
		utilisateurFromDB.getGroupes().add(groupeFromDB);
		
		//mise à jour
		groupeRepository.save(groupeFromDB);
		utilisateurRepository.save(utilisateurFromDB);
		return true;
	}

    @Override
	public String getAccesCode(long idGroupe, long idUtilisateur) throws Exception{
		Groupe groupeFromDB = groupeRepository.findById(idGroupe).orElse(null);
		Utilisateur utilisateurFromDB = utilisateurRepository.findById(idUtilisateur).orElse(null);
		if(utilisateurFromDB == null) {
			throw new Exception("Utilisateur not found");
		}
		if(groupeFromDB == null) {
			throw new Exception("Groupe not found.");
		}
		if(!utilisateurFromDB.equals(groupeFromDB.getOwner())) {
			throw new Exception("Vous n'êtes par le propriétaire du groupe, Vous ne pouvez pas avoir le code d'accès!");
		}
		//si le groupe est publique
		if(groupeFromDB.getCodeAcces() == null) {
			return null;
		}
		return groupeFromDB.getCodeAcces();
	}

    @Override
    public boolean supprimerGroupe(long id) {
        if (groupeRepository.existsById(id)) {
            groupeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public long totalGroupes() {
        return groupeRepository.count();
    }

    @Override
    public long totalGroupesPubliques() {
        return groupeRepository.countByVisible(true);
    }

    @Override
    public long totalGroupesPrives() {
        return groupeRepository.countByVisible(false);
    }
    
    private UtilisateurDto convertToDto(Utilisateur utilisateur) {
        return new UtilisateurDto(
            utilisateur.getId(),
            utilisateur.getNom(),
            utilisateur.getPrenom(),
            utilisateur.getUsername(),
            utilisateur.getEmail()
             );
    }
    private GroupeDto convertGroupeToDto(Groupe groupe) {
    	return new GroupeDto(
    			groupe.getId(),
    			groupe.getNom(),
    			groupe.getDescription(),
    			groupe.getCodeAcces(),
    			groupe.isVisible()
    			);
    }

}
