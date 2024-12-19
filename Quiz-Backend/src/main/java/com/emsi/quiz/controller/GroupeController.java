package com.emsi.quiz.controller;

import com.emsi.quiz.dto.GroupeDto;
import com.emsi.quiz.dto.GroupeRequest;
import com.emsi.quiz.dto.UtilisateurDto;
import com.emsi.quiz.entity.Groupe;
import com.emsi.quiz.entity.Utilisateur;
import com.emsi.quiz.service.GroupeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groupes")
public class GroupeController {

    @Autowired
    private GroupeService groupeService;

    @PostMapping
    public ResponseEntity<Groupe> createGroupe(@RequestBody GroupeRequest groupeRequest) {
        try {
            // Convert the DTO to the Groupe entity
            Groupe groupe = groupeRequest.toGroupe();
            
            // Save the groupe using the service, passing ownerId and memberIds
            Groupe createdGroupe = groupeService.save(groupe, groupeRequest.getOwnerId(), groupeRequest.getMemberIds());
            
            return ResponseEntity.ok(createdGroupe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping
    public ResponseEntity<List<Groupe>> getAllGroupes() {
        return ResponseEntity.ok(groupeService.findAll());
    }

    @GetMapping("/public")
    public ResponseEntity<List<Groupe>> getPublicGroupes() {
        return ResponseEntity.ok(groupeService.getPublicGroupes());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Groupe>> searchGroupes(@RequestParam String nom) {
        return ResponseEntity.ok(groupeService.findByName(nom));
    }

    //pour rejoindre un groupe privé
    @PostMapping("/rejoindre/{codeAcces}/{idUtilisateur}")
	public ResponseEntity<String> rejoindreGroupe(@PathVariable String codeAcces, @PathVariable long idUtilisateur) throws Exception{
		 try {
	            boolean result = groupeService.rejoindreGroupePrivate(codeAcces, idUtilisateur);
	            if (result) {
	                return ResponseEntity.ok("Vous avez rejoint le groupe privé.");
	            } else {
	                return ResponseEntity.status(400).body("Vous êtes déjà membre du groupe.");
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Une erreur est survenue.");
	       }
	} 

  //rejoindre un groupe publique
	  @PostMapping("/public/join/{idGroupe}/{idUtilisateur}")
	    public ResponseEntity<String> rejoindreGroupePublic(@PathVariable long idGroupe, @PathVariable long idUtilisateur) {
	        try {
	            boolean result = groupeService.rejoindreGroupePublic(idGroupe, idUtilisateur);
	            if (result) {
	                return ResponseEntity.ok("Vous avez rejoint le groupe public.");
	            } else {
	                return ResponseEntity.status(400).body("Vous êtes déjà membre du groupe.");
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Une erreur est survenue.");
	        }
	    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<UtilisateurDto>> getMembers(@PathVariable long id) {
        return ResponseEntity.ok(groupeService.getAllGroupeMembers(id));
    }

    @GetMapping("/user/{idUtilisateur}")
    public ResponseEntity<List<GroupeDto>> getUserGroupes(@PathVariable long idUtilisateur) {
        return ResponseEntity.ok(groupeService.getAllGroupesByUser(idUtilisateur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteGroupe(@PathVariable long id) {
        boolean result = groupeService.supprimerGroupe(id);
        return result ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }
}
