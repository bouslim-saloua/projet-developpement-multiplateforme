package com.emsi.quiz.service.impl;

import com.emsi.quiz.entity.Utilisateur;
import com.emsi.quiz.repository.UtilisateurRepository;
import com.emsi.quiz.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'ID : " + id));
    }

    @Override
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        // Encoder le mot de passe avant de l'enregistrer
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        if (!utilisateurRepository.existsById(utilisateur.getId())) {
            throw new RuntimeException("Utilisateur introuvable pour la mise à jour !");
        }
        // Vous pouvez aussi encoder à nouveau le mot de passe en cas de modification
        if (utilisateur.getPassword() != null) {
            utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        }
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public void deleteUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur introuvable pour la suppression !");
        }
        utilisateurRepository.deleteById(id);
    }
}
