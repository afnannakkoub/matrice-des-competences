package com.matrice.backend.service;

import com.matrice.backend.DTO.LoginRequest;
import com.matrice.backend.DTO.LoginResponse;
import com.matrice.backend.entity.Utilisateur;
import com.matrice.backend.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;

    public AuthService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public LoginResponse login(LoginRequest request) {

        // ==========================================
        // 1. Find user by email
        // ==========================================

        Utilisateur utilisateur =
                utilisateurRepository.findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Email ou mot de passe incorrect."
                                )
                        );


        // ==========================================
        // 2. Check password
        // ==========================================

        if (!utilisateur.getMotDePasse()
                .equals(request.getMotDePasse())) {

            throw new RuntimeException(
                    "Email ou mot de passe incorrect."
            );
        }


        // ==========================================
        // 3. Check account active
        // ==========================================

        if (Boolean.FALSE.equals(utilisateur.getActif())) {

            throw new RuntimeException(
                    "Ce compte est désactivé."
            );
        }


        // ==========================================
        // 4. Get role
        // ==========================================

        String role = null;

        if (utilisateur.getRole() != null) {
            role = utilisateur.getRole().getNom();
        }


        // ==========================================
        // 5. Return safe response
        // ==========================================

        return new LoginResponse(
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                role,
                utilisateur.getPoste(),
                utilisateur.getDepartement()
        );
    }
}