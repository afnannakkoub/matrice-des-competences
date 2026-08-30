package com.matrice.backend.controller;

import com.matrice.backend.DTO.EmployeeSkillMatrixDTO;
import com.matrice.backend.DTO.SkillMatrixRowDTO;
import com.matrice.backend.entity.Utilisateur;
import com.matrice.backend.service.UtilisateurService;
import org.springframework.web.bind.annotation.*;
import com.matrice.backend.DTO.ManagerDashboardDTO;
import com.matrice.backend.DTO.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService service;

    public UtilisateurController(UtilisateurService service) {
        this.service = service;
    }

    // ==========================
    // GET ALL
    // ==========================
    @GetMapping
    public List<Utilisateur> getAll() {
        return service.getAllUtilisateurs();
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        return service.login(request);
    }

    // ==========================
    // GET BY ID
    // ==========================
    @GetMapping("/{id}")
    public Optional<Utilisateur> getById(@PathVariable Long id) {
        return service.getUtilisateurById(id);
    }


    // ==========================
    // CREATE
    // ==========================
    @PostMapping
    public Utilisateur create(@RequestBody Utilisateur utilisateur) {
        return service.save(utilisateur);
    }

    // ==========================
    // UPDATE
    // ==========================
    @PutMapping("/{id}")
    public Utilisateur update(
            @PathVariable Long id,
            @RequestBody Utilisateur utilisateur) {

        return service.update(id, utilisateur);
    }

    // ==========================
    // DELETE
    // ==========================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

// =========================================
// MANAGER
// Get manager's team
// =========================================

    @GetMapping("/manager/{managerId}/equipe")
    public List<Utilisateur> getEquipe(
            @PathVariable Long managerId) {

        return service.getEquipe(managerId);

    }

    @GetMapping("/manager/{managerId}/dashboard")
    public ManagerDashboardDTO dashboard(
            @PathVariable Long managerId) {

        return service.getDashboard(managerId);

    }
    @GetMapping("/manager/{managerId}/matrix")
    public List<SkillMatrixRowDTO> getSkillMatrix(
            @PathVariable Long managerId) {

        return service.getTeamSkillMatrix(managerId);
    }

    @GetMapping("/postes")
    public List<String> getPostes() {

        return service.getPostes();

    }
    // =========================================
// EMPLOYEE SKILL MATRIX
// =========================================

    @GetMapping("/{utilisateurId}/skill-matrix")
    public List<EmployeeSkillMatrixDTO> getEmployeeSkillMatrix(
            @PathVariable Long utilisateurId) {

        return service.getEmployeeSkillMatrix(utilisateurId);

    }

    @GetMapping("/manager/{managerId}/validation-matrix")
    public List<PosteSkillMatrixRowDTO> getValidationMatrix(
            @PathVariable Long managerId) {

        return service.getValidationMatrix(managerId);
    }

    // =====================================================
// EMPLOYEE - UPDATE PROFILE
// =====================================================

    @PutMapping("/{id}/profile")
    public Utilisateur updateProfile(
            @PathVariable Long id,
            @RequestBody Utilisateur utilisateur
    ) {

        return service.updateProfile(
                id,
                utilisateur
        );
    }

}


