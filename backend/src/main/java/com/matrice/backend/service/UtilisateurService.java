package com.matrice.backend.service;

import com.matrice.backend.DTO.*;
import com.matrice.backend.entity.*;
import com.matrice.backend.repository.PosteCompetenceRepository;
import com.matrice.backend.repository.UtilisateurCompetenceRepository;
import com.matrice.backend.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository repository;
    private final UtilisateurCompetenceRepository utilisateurCompetenceRepository;
    private final PosteCompetenceRepository posteCompetenceRepository;

    public UtilisateurService(
            UtilisateurRepository repository,
            PosteCompetenceRepository posteCompetenceRepository,
            UtilisateurCompetenceRepository utilisateurCompetenceRepository
    ) {
        this.repository = repository;
        this.posteCompetenceRepository = posteCompetenceRepository;
        this.utilisateurCompetenceRepository = utilisateurCompetenceRepository;
    }

    // =====================================================
    // EMPLOYEE SKILL MATRIX
    // =====================================================



    public List<EmployeeSkillMatrixDTO> getEmployeeSkillMatrix(
            Long utilisateurId) {

        // ==========================================
        // 1. Find employee
        // ==========================================

        Utilisateur utilisateur =
                repository.findById(utilisateurId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Utilisateur introuvable : "
                                                + utilisateurId
                                )
                        );


        // ==========================================
        // 2. Get employee position
        // ==========================================

        String poste = utilisateur.getPoste();

        if (poste == null || poste.isBlank()) {

            throw new RuntimeException(
                    "L'utilisateur n'a pas de poste."
            );
        }


        // ==========================================
        // 3. Get competencies required by the POSTE
        // ==========================================

        List<PosteCompetence> posteCompetences =
                posteCompetenceRepository.findByPoste(poste);


        // ==========================================
        // 4. Get competencies associated with employee
        // ==========================================

        List<UtilisateurCompetence> evaluations =
                utilisateurCompetenceRepository
                        .findByUtilisateurId(utilisateurId);


        // ==========================================
        // 5. Use Map to avoid duplicate competencies
        // ==========================================

        Map<Long, EmployeeSkillMatrixDTO> matrix =
                new HashMap<>();


        // ==========================================
        // 6. ADD POSTE REQUIRED COMPETENCIES
        // ==========================================

        for (PosteCompetence pc : posteCompetences) {

            if (pc.getCompetence() == null) {
                continue;
            }


            Long competenceId =
                    pc.getCompetence().getId();


            EmployeeSkillMatrixDTO dto =
                    new EmployeeSkillMatrixDTO();


            // ------------------------------------------
            // Competence
            // ------------------------------------------

            dto.setCompetenceId(
                    competenceId
            );

            dto.setCompetence(
                    pc.getCompetence().getNom()
            );


            // ------------------------------------------
            // Required level
            // ------------------------------------------

            dto.setNiveauRequis(
                    pc.getNiveauRequis()
            );


            // ==========================================
            // Find employee evaluation
            // ==========================================

            UtilisateurCompetence evaluation =
                    evaluations.stream()
                            .filter(e ->
                                    e.getCompetence() != null
                                            &&
                                            e.getCompetence()
                                                    .getId()
                                                    .equals(competenceId)
                            )
                            .findFirst()
                            .orElse(null);


            // ==========================================
            // NO EMPLOYEE EVALUATION
            // ==========================================

            if (evaluation == null) {
                dto.setEvaluationId(null);

                dto.setNiveauActuel(null);

                dto.setNiveauEvaluation(null);

                dto.setGap(null);

                dto.setStatut("NON_EVALUE");

            }


            // ==========================================
            // EMPLOYEE EVALUATION EXISTS
            // ==========================================

            else {

                dto.setEvaluationId(
                        evaluation.getId()
                );


                // Employee proposed level
                dto.setNiveauActuel(
                        evaluation.getNiveauEmploye()
                );


                // Manager validated level
                dto.setNiveauEvaluation(
                        evaluation.getNiveauValide()
                );


                // --------------------------------------
                // GAP
                // Required - Manager validated
                // --------------------------------------

                if (evaluation.getNiveauValide() != null
                        && pc.getNiveauRequis() != null) {

                    int gap =
                            pc.getNiveauRequis()
                                    - evaluation.getNiveauValide();

                    dto.setGap(
                            Math.max(gap, 0)
                    );

                } else {

                    dto.setGap(null);
                }


                // --------------------------------------
                // Status
                // --------------------------------------

                dto.setStatut(
                        evaluation.getStatut() != null
                                ? evaluation.getStatut().name()
                                : null
                );
            }


            // Add to map
            matrix.put(
                    competenceId,
                    dto
            );
        }


        // ==========================================
        // 7. ADD EMPLOYEE'S EXTRA COMPETENCIES
        // ==========================================


        for (UtilisateurCompetence evaluation : evaluations) {

            if (evaluation.getCompetence() == null) {
                continue;
            }


            Long competenceId =
                    evaluation.getCompetence().getId();


            // ------------------------------------------
            // by the employee's poste.
            // ------------------------------------------

            if (matrix.containsKey(competenceId)) {
                continue;
            }


            EmployeeSkillMatrixDTO dto =
                    new EmployeeSkillMatrixDTO();


            // ------------------------------------------
            // Competence
            // ------------------------------------------

            dto.setCompetenceId(
                    competenceId
            );

            dto.setCompetence(
                    evaluation.getCompetence().getNom()
            );


            // ------------------------------------------
            // Not required by this poste
            // ------------------------------------------

            dto.setNiveauRequis(null);


            // ------------------------------------------
            // Employee level
            // ------------------------------------------

            dto.setNiveauActuel(
                    evaluation.getNiveauEmploye()
            );


            // ------------------------------------------
            // Manager evaluation
            // ------------------------------------------

            dto.setNiveauEvaluation(
                    evaluation.getNiveauValide()
            );


            // ------------------------------------------
            // No required level
            // therefore no gap
            // ------------------------------------------

            dto.setGap(null);


            // ------------------------------------------
            // Status
            // ------------------------------------------

            dto.setStatut(
                    evaluation.getStatut() != null
                            ? evaluation.getStatut().name()
                            : null
            );


            // Add extra competence
            matrix.put(
                    competenceId,
                    dto
            );
        }


        // ==========================================
        // 8. Convert Map -> List
        // ==========================================

        return new ArrayList<>(
                matrix.values()
        );
    }

    // =====================================================
    // CREATE
    // =====================================================

    public Utilisateur save(Utilisateur utilisateur) {

        if (repository.existsByEmail(utilisateur.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        return repository.save(utilisateur);
    }

    // =====================================================
    // READ ALL
    // =====================================================

    public List<Utilisateur> getAllUtilisateurs() {
        return repository.findAll();
    }

    // =====================================================
    // READ BY ID
    // =====================================================

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return repository.findById(id);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public Utilisateur update(
            Long id,
            Utilisateur utilisateur
    ) {

        Utilisateur existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Utilisateur introuvable"
                ));

        existing.setNom(utilisateur.getNom());
        existing.setPrenom(utilisateur.getPrenom());
        existing.setEmail(utilisateur.getEmail());
        existing.setMotDePasse(utilisateur.getMotDePasse());
        existing.setRole(utilisateur.getRole());
        existing.setManager(utilisateur.getManager());
        existing.setActif(utilisateur.getActif());
        existing.setPoste(utilisateur.getPoste());
        existing.setDepartement(utilisateur.getDepartement());

        return repository.save(existing);
    }

    // =====================================================
    // DELETE
    // =====================================================

    public void delete(Long id) {
        repository.deleteById(id);
    }

    // =====================================================
    // MANAGER - GET TEAM
    // =====================================================

    public List<Utilisateur> getEquipe(Long managerId) {
        return repository.findByManagerId(managerId);
    }

    // =====================================================
    // MANAGER DASHBOARD
    // =====================================================

    public ManagerDashboardDTO getDashboard(Long managerId) {

        Utilisateur manager = repository.findById(managerId)
                .orElseThrow(() -> new RuntimeException(
                        "Manager introuvable"
                ));

        Long nbEmployes =
                repository.countByManagerId(managerId);

        Long enAttente =
                utilisateurCompetenceRepository
                        .countByUtilisateurManagerIdAndStatut(
                                managerId,
                                StatutEvaluation.EN_ATTENTE
                        );

        Long validees =
                utilisateurCompetenceRepository
                        .countByManagerId(managerId);

        return new ManagerDashboardDTO(
                manager.getNom() + " " + manager.getPrenom(),
                nbEmployes,
                enAttente,
                validees
        );
    }

    // =====================================================
    // TEAM SKILL MATRIX
    // =====================================================

    public List<SkillMatrixRowDTO> getTeamSkillMatrix(
            Long managerId
    ) {

        List<Utilisateur> equipe =
                repository.findByManagerId(managerId);

        List<SkillMatrixRowDTO> matrix =
                new ArrayList<>();

        for (Utilisateur utilisateur : equipe) {

            List<UtilisateurCompetence> evaluations =
                    utilisateurCompetenceRepository
                            .findByUtilisateurId(
                                    utilisateur.getId()
                            );

            Map<String, Integer> competences =
                    new HashMap<>();

            for (UtilisateurCompetence evaluation : evaluations) {

                if (evaluation.getStatut()
                        == StatutEvaluation.VALIDE) {

                    competences.put(
                            evaluation.getCompetence().getNom(),
                            evaluation.getNiveauValide()
                    );
                }
            }

            SkillMatrixRowDTO row =
                    new SkillMatrixRowDTO();

            row.setUtilisateurId(
                    utilisateur.getId()
            );

            row.setNom(
                    utilisateur.getNom()
            );

            row.setPrenom(
                    utilisateur.getPrenom()
            );

            row.setCompetences(
                    competences
            );

            matrix.add(row);
        }

        return matrix;
    }

    // =====================================================
    // GET DISTINCT POSTES
    // =====================================================

    public List<String> getPostes() {
        return repository.findDistinctPostes();
    }

    // =====================================================
    // VALIDATION MATRIX
    // TEAM COMPETENCY VISUALIZATION
    // =====================================================

    public List<PosteSkillMatrixRowDTO> getValidationMatrix(
            Long managerId) {

        // ==========================================
        // 1. Get manager's team
        // ==========================================

        List<Utilisateur> equipe =
                repository.findByManagerId(managerId);


        // ==========================================
        // 2. Get all posts defined in poste_competence
        // ==========================================

        List<String> postes =
                posteCompetenceRepository.findAll()
                        .stream()
                        .map(PosteCompetence::getPoste)
                        .filter(poste ->
                                poste != null &&
                                        !poste.isBlank()
                        )
                        .distinct()
                        .toList();


        List<PosteSkillMatrixRowDTO> result =
                new ArrayList<>();


        // ==========================================
        // 3. Loop through posts
        // ==========================================

        for (String poste : postes) {

            List<PosteCompetence> requiredCompetences =
                    posteCompetenceRepository.findByPoste(poste);


            if (requiredCompetences.isEmpty()) {
                continue;
            }


            Map<Long, Integer> niveaux =
                    new HashMap<>();

            Map<Long, Integer> pourcentages =
                    new HashMap<>();

            Map<Long, List<CompetencyScoreDTO>> details =
                    new HashMap<>();


            // ==========================================
            // 4. Employees having this post
            // ==========================================

            List<Utilisateur> employeesForPost =
                    equipe.stream()
                            .filter(employee ->
                                    employee.getPoste() != null &&
                                            employee.getPoste()
                                                    .equalsIgnoreCase(poste)
                            )
                            .toList();


            // ==========================================
            // 5. Calculate every employee
            // ==========================================

            for (Utilisateur employee : employeesForPost) {

                List<UtilisateurCompetence> evaluations =
                        utilisateurCompetenceRepository
                                .findByUtilisateurId(
                                        employee.getId()
                                );


                int totalRequired = 0;

                int totalValidated = 0;


                List<CompetencyScoreDTO> employeeDetails =
                        new ArrayList<>();


                // ==========================================
                // 6. Process required competencies
                // ==========================================

                for (PosteCompetence pc :
                        requiredCompetences) {

                    if (pc.getCompetence() == null) {
                        continue;
                    }


                    Integer required =
                            pc.getNiveauRequis();

                    if (required == null) {
                        continue;
                    }


                    Long competenceId =
                            pc.getCompetence().getId();


                    // ==========================================
                    // Add required level to denominator
                    // ==========================================

                    totalRequired += required;


                    // ==========================================
                    // Find employee competency
                    // ==========================================

                    UtilisateurCompetence evaluation =
                            evaluations.stream()
                                    .filter(e ->
                                            e.getCompetence() != null
                                                    &&
                                                    e.getCompetence()
                                                            .getId()
                                                            .equals(competenceId)
                                    )
                                    .findFirst()
                                    .orElse(null);


                    // ==========================================
                    // Default values
                    // ==========================================

                    Integer niveauEmploye = null;

                    Integer niveauValide = null;

                    Integer niveauPrisEnCompte = 0;

                    String statut = "NON_EVALUE";


                    // ==========================================
                    // If employee has this competency
                    // ==========================================

                    if (evaluation != null) {

                        niveauEmploye =
                                evaluation.getNiveauEmploye();

                        niveauValide =
                                evaluation.getNiveauValide();


                        statut =
                                evaluation.getStatut() != null
                                        ? evaluation.getStatut().name()
                                        : "NON_EVALUE";


                        // ======================================
                        // Manager validation has priority
                        // ======================================

                        if (evaluation.getStatut()
                                == StatutEvaluation.VALIDE
                                && niveauValide != null) {

                            niveauPrisEnCompte =
                                    niveauValide;

                        }

                        // ======================================
                        // Otherwise use employee declaration
                        // ======================================

                        else if (niveauEmploye != null) {

                            niveauPrisEnCompte =
                                    niveauEmploye;
                        }
                    }


                    // ==========================================
                    // Never exceed required level
                    // ==========================================

                    niveauPrisEnCompte =
                            Math.min(
                                    niveauPrisEnCompte,
                                    required
                            );


                    // ==========================================
                    // Add ONCE to total
                    // ==========================================

                    totalValidated +=
                            niveauPrisEnCompte;


                    // ==========================================
                    // Build tooltip detail
                    // ==========================================

                    CompetencyScoreDTO competencyDTO =
                            new CompetencyScoreDTO();


                    competencyDTO.setCompetenceId(
                            competenceId
                    );

                    competencyDTO.setCompetence(
                            pc.getCompetence().getNom()
                    );

                    competencyDTO.setNiveauRequis(
                            required
                    );

                    competencyDTO.setNiveauEmploye(
                            niveauEmploye
                    );

                    competencyDTO.setNiveauValide(
                            niveauValide
                    );

                    competencyDTO.setNiveauPrisEnCompte(
                            niveauPrisEnCompte
                    );

                    competencyDTO.setStatut(
                            statut
                    );


                    employeeDetails.add(
                            competencyDTO
                    );
                }


                // ==========================================
                // 7. Calculate percentage
                // ==========================================

                int percentage = 0;


                if (totalRequired > 0) {

                    percentage =
                            Math.round(
                                    ((float) totalValidated
                                            / totalRequired)
                                            * 100
                            );
                }


                // ==========================================
                // 8. Convert percentage -> level
                // ==========================================

                int level = 0;


                if (percentage >= 90) {

                    level = 4;

                } else if (percentage >= 75) {

                    level = 3;

                } else if (percentage >= 50) {

                    level = 2;

                } else if (percentage > 0) {

                    level = 1;
                }


                // ==========================================
                // 9. Save employee result
                // ==========================================

                niveaux.put(
                        employee.getId(),
                        level
                );

                pourcentages.put(
                        employee.getId(),
                        percentage
                );


                // ==========================================
                // 10. Save competency details
                // ==========================================

                details.put(
                        employee.getId(),
                        employeeDetails
                );
            }


            // ==========================================
            // 11. Create post row
            // ==========================================

            PosteSkillMatrixRowDTO row =
                    new PosteSkillMatrixRowDTO();


            row.setPoste(poste);

            row.setNiveaux(niveaux);

            row.setPourcentages(pourcentages);

            row.setDetails(details);


            result.add(row);
        }


        return result;
    }

    public LoginResponse login(LoginRequest request) {

        Utilisateur utilisateur =
                repository.findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Email ou mot de passe incorrect"
                                )
                        );

        if (!utilisateur.getMotDePasse()
                .equals(request.getMotDePasse())) {

            throw new RuntimeException(
                    "Email ou mot de passe incorrect"
            );
        }

        if (!Boolean.TRUE.equals(utilisateur.getActif())) {

            throw new RuntimeException(
                    "Ce compte est désactivé"
            );
        }

        return new LoginResponse(
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getRole().getNom(),
                utilisateur.getPoste(),
                utilisateur.getDepartement()
        );
    }

    // =====================================================
// EMPLOYEE - UPDATE PROFILE
// =====================================================

    public Utilisateur updateProfile(
            Long id,
            Utilisateur utilisateur
    ) {

        Utilisateur existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Utilisateur introuvable"
                                )
                        );

        // Only employee-editable information
        existing.setNom(
                utilisateur.getNom()
        );

        existing.setPrenom(
                utilisateur.getPrenom()
        );

        existing.setEmail(
                utilisateur.getEmail()
        );

        return repository.save(existing);
    }

}

