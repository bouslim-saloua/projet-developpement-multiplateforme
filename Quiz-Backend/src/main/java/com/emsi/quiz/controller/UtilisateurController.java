package com.emsi.quiz.controller;



import com.emsi.quiz.dto.LoginRequest;
import com.emsi.quiz.dto.SignupRequest;
import com.emsi.quiz.entity.CustomUtilisateurDetails;
import com.emsi.quiz.entity.Utilisateur;
import com.emsi.quiz.repository.UtilisateurRepository;
import com.emsi.quiz.security.JwtUtils;
import com.google.zxing.WriterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {
	@Autowired
	UtilisateurRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtil;
    @Autowired
	PasswordEncoder encoder;

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
    	Optional<Utilisateur> user = userRepository.findByUsername(loginRequest.getUsername());
    	if(!(userRepository.existsByUsername(loginRequest.getUsername()))){
			return ResponseEntity
					.badRequest()
					.body(Map.of("error", "Invalid username or password"));

		}else if (user.isPresent()) {
			Utilisateur utilisateur = user.get();
			if (!(encoder.matches(loginRequest.getPassword(), utilisateur.getPassword()))) {
				return ResponseEntity.badRequest().body(Map.of("error","Error: Password doesn't match!"));
			}

		}
    	
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.generateJwtToken(authentication);

            CustomUtilisateurDetails userDetails = (CustomUtilisateurDetails) authentication.getPrincipal();

            Map<String, Object> response = Map.of(
                "id", userDetails.getId(),
                "email", userDetails.getEmail(),
                "nom", userDetails.getNom(),
                "prenom", userDetails.getPrenom(),
                "jwt", jwt
            );

            return ResponseEntity.ok(response);

        
    }
    
    @PostMapping("/signup")
	public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest) throws WriterException {
		//Error when email is already in use
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(Map.of("error","Error: Email is already in use!"));
		}
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(Map.of("error","Error: Username is already in use!"));
		}
		try {
			// Create new demandeur's account
			Utilisateur utilisateur = new Utilisateur( signUpRequest.getNom(), signUpRequest.getPrenom(),signUpRequest.getUsername(), signUpRequest.getEmail()
					,encoder.encode(signUpRequest.getPassword()),signUpRequest.getRole());
			userRepository.save(utilisateur);
			return ResponseEntity.ok(Map.of("succes","Account registered successfully!"));
		} catch (Exception e) {
			// Handle exception
			e.printStackTrace();

			return ResponseEntity.ok(Map.of("error","ERROR REGISTERING THIS USER!"));

		}
	}
    
}

