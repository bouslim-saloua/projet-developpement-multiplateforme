package com.emsi.quiz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUtilisateurDetails implements UserDetails {
	private Collection<? extends GrantedAuthority> authorities;
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String username;
    private String password;
    
    // Constructeur
    public CustomUtilisateurDetails(Long id, String nom, String prenom, String email, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
    return this.authorities;
    }
    
    // Création d'un CustomUtilisateurDetails à partir d'un Utilisateur
    public static CustomUtilisateurDetails fromUtilisateur(Utilisateur utilisateur) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(utilisateur.getRole().name()));
        return new CustomUtilisateurDetails(
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getUsername(),
                utilisateur.getPassword(),
                authorities
        );
    }

    
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Méthode qui peut être utilisée pour vérifier si le compte est expiré
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Vérifie si l'utilisateur est bloqué ou non
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Vérifie si les crédentials de l'utilisateur sont expirés
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Vérifie si l'utilisateur est actif
    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getters et Setters
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    public void setUsername(String username) {
        this.username = username;
    }

   
    public void setPassword(String password) {
        this.password = password;
    }

    

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
