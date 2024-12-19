package com.emsi.quiz.service;

import com.emsi.quiz.entity.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    List<Utilisateur> getAllUtilisateurs();
    Utilisateur getUtilisateurById(Long id);
    Utilisateur saveUtilisateur(Utilisateur utilisateur);
    Utilisateur updateUtilisateur(Utilisateur utilisateur);
    void deleteUtilisateur(Long id);
    
   
}
