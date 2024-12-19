package com.emsi.quiz.service.impl;



import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emsi.quiz.entity.CustomUtilisateurDetails;
import com.emsi.quiz.entity.Utilisateur;
import com.emsi.quiz.repository.UtilisateurRepository;


@RequiredArgsConstructor
@Service
public class JpaUtilisateurDetailsService implements UserDetailsService{

	final UtilisateurRepository utilisateurRepository;
	  @Override
	    public CustomUtilisateurDetails loadUserByUsername(String username) {
	        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("Problem during authentication!");

	        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
	                .orElseThrow(s);

	        // Use the fromUtilisateur method here
	        return CustomUtilisateurDetails.fromUtilisateur(utilisateur);
	    }

	
}

