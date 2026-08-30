package com.matrice.backend.service;

import com.matrice.backend.DTO.StatisticsDTO;
import com.matrice.backend.entity.Competence;
import com.matrice.backend.entity.PosteCompetence;
import com.matrice.backend.entity.StatutEvaluation;
import com.matrice.backend.entity.Utilisateur;
import com.matrice.backend.entity.UtilisateurCompetence;
import com.matrice.backend.repository.PosteCompetenceRepository;
import com.matrice.backend.repository.UtilisateurCompetenceRepository;
import com.matrice.backend.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final UtilisateurRepository utilisateurRepository;

    private final UtilisateurCompetenceRepository
            utilisateurCompetenceRepository;

    private final PosteCompetenceRepository
            posteCompetenceRepository;


    public StatisticsService(
            UtilisateurRepository utilisateurRepository,
            UtilisateurCompetenceRepository
                    utilisateurCompetenceRepository,
            PosteCompetenceRepository
                    posteCompetenceRepository) {

        this.utilisateurRepository =
                utilisateurRepository;

        this.utilisateurCompetenceRepository =
                utilisateurCompetenceRepository;

        this.posteCompetenceRepository =
                posteCompetenceRepository;
    }


    // =====================================================
    // MAIN STATISTICS
    // =====================================================

    public StatisticsDTO getStatistics(Long managerId) {

        // ==========================================
        // 1. GET MANAGER'S TEAM
        // ==========================================

        List<Utilisateur> employees =
                utilisateurRepository
                        .findByManagerId(managerId);


        StatisticsDTO statistics =
                new StatisticsDTO();


        // ==========================================
        // 2. TOTAL EMPLOYEES
        // ==========================================

        statistics.setTotalEmployees(
                employees.size()
        );


        // ==========================================
        // 3. COUNTERS
        // ==========================================

        long totalCompetencies = 0;

        long validatedCompetencies = 0;

        long pendingCompetencies = 0;

        double totalLevels = 0;

        long levelCount = 0;

        double totalPercentage = 0;


        // ==========================================
        // LEVEL DISTRIBUTION
        // ==========================================

        long level0 = 0;
        long level1 = 0;
        long level2 = 0;
        long level3 = 0;
        long level4 = 0;


        // ==========================================
        // EMPLOYEE STATISTICS
        // ==========================================

        List<StatisticsDTO.EmployeeStatisticsDTO>
                employeeStatistics =
                new ArrayList<>();


        // ==========================================
        // POSITION GROUPING
        // ==========================================

        Map<String, List<Double>>
                positionPercentages =
                new HashMap<>();

        Map<String, List<Double>>
                positionLevels =
                new HashMap<>();


        // ==========================================
        // 4. PROCESS EVERY EMPLOYEE
        // ==========================================

        for (Utilisateur employee : employees) {

            Long employeeId =
                    employee.getId();


            // --------------------------------------
            // Required competencies for position
            // --------------------------------------

            String poste =
                    employee.getPoste();


            List<PosteCompetence>
                    requiredCompetencies =
                    new ArrayList<>();


            if (poste != null && !poste.isBlank()) {

                requiredCompetencies =
                        posteCompetenceRepository
                                .findByPoste(poste);
            }


            // --------------------------------------
            // Employee evaluations
            // --------------------------------------

            List<UtilisateurCompetence>
                    evaluations =
                    utilisateurCompetenceRepository
                            .findByUtilisateurId(
                                    employeeId
                            );


            // ======================================
            // EMPLOYEE CALCULATION
            // ======================================

            int employeeTotalRequired = 0;

            int employeeTotalValidated = 0;

            int employeeValidatedCount = 0;

            double employeeLevelTotal = 0;

            int employeeLevelCount = 0;


            // ======================================
            // PROCESS REQUIRED COMPETENCIES
            // ======================================

            for (PosteCompetence pc :
                    requiredCompetencies) {

                if (pc.getCompetence() == null) {
                    continue;
                }


                Integer required =
                        pc.getNiveauRequis();


                if (required == null ||
                        required <= 0) {

                    continue;
                }


                Long competenceId =
                        pc.getCompetence().getId();


                employeeTotalRequired +=
                        required;


                totalCompetencies++;


                // ----------------------------------
                // Find employee evaluation
                // ----------------------------------

                UtilisateurCompetence evaluation =
                        evaluations.stream()
                                .filter(e ->
                                        e.getCompetence() != null
                                                &&
                                                e.getCompetence()
                                                        .getId()
                                                        .equals(
                                                                competenceId
                                                        )
                                )
                                .findFirst()
                                .orElse(null);


                Integer niveauPrisEnCompte =
                        0;


                if (evaluation != null) {

                    // --------------------------------
                    // Manager validation
                    // --------------------------------

                    if (evaluation.getStatut()
                            == StatutEvaluation.VALIDE
                            &&
                            evaluation.getNiveauValide()
                                    != null) {

                        niveauPrisEnCompte =
                                evaluation
                                        .getNiveauValide();

                        validatedCompetencies++;

                        employeeValidatedCount++;

                    }

                    // --------------------------------
                    // Not validated
                    // --------------------------------

                    else {

                        pendingCompetencies++;
                    }

                }


                // ----------------------------------
                // Never exceed required level
                // ----------------------------------

                niveauPrisEnCompte =
                        Math.min(
                                niveauPrisEnCompte,
                                required
                        );


                employeeTotalValidated +=
                        niveauPrisEnCompte;


                // ----------------------------------
                // Level distribution
                // ----------------------------------


                // ----------------------------------
                // Average level
                // ----------------------------------

                if (evaluation != null) {

                    employeeLevelTotal +=
                            niveauPrisEnCompte;

                    employeeLevelCount++;

                    totalLevels +=
                            niveauPrisEnCompte;

                    levelCount++;
                }
            }


            // ======================================
            // EMPLOYEE PERCENTAGE
            // ======================================

            double employeePercentage = 0;


            if (employeeTotalRequired > 0) {

                employeePercentage =
                        ((double)
                                employeeTotalValidated
                                /
                                employeeTotalRequired)
                                * 100;
            }

            int employeeLevel = 0;

            if (employeePercentage >= 90) {

                employeeLevel = 4;

            } else if (employeePercentage >= 75) {

                employeeLevel = 3;

            } else if (employeePercentage >= 50) {

                employeeLevel = 2;

            } else if (employeePercentage > 0) {

                employeeLevel = 1;

            } else {

                employeeLevel = 0;
            }


            // ======================================
// EMPLOYEE LEVEL DISTRIBUTION
// ======================================

            switch (employeeLevel) {

                case 0:
                    level0++;
                    break;

                case 1:
                    level1++;
                    break;

                case 2:
                    level2++;
                    break;

                case 3:
                    level3++;
                    break;

                case 4:
                    level4++;
                    break;

                default:
                    break;
            }


            // ======================================
            // EMPLOYEE AVERAGE LEVEL
            // ======================================

            double employeeAverageLevel = 0;


            if (employeeLevelCount > 0) {

                employeeAverageLevel =
                        employeeLevelTotal
                                /
                                employeeLevelCount;
            }


            totalPercentage +=
                    employeePercentage;


            // ======================================
            // EMPLOYEE DTO
            // ======================================

            StatisticsDTO.EmployeeStatisticsDTO
                    employeeDTO =
                    new StatisticsDTO
                            .EmployeeStatisticsDTO(
                            employee.getId(),
                            employee.getNom(),
                            employee.getPrenom(),
                            employee.getPoste(),
                            round(employeeAverageLevel),
                            round(employeePercentage),
                            employeeValidatedCount,
                            requiredCompetencies.size()
                    );


            employeeStatistics.add(
                    employeeDTO
            );


            // ======================================
            // POSITION STATISTICS
            // ======================================

            String position =
                    employee.getPoste();


            if (position != null &&
                    !position.isBlank()) {

                positionPercentages
                        .computeIfAbsent(
                                position,
                                k -> new ArrayList<>()
                        )
                        .add(employeePercentage);


                positionLevels
                        .computeIfAbsent(
                                position,
                                k -> new ArrayList<>()
                        )
                        .add(employeeAverageLevel);
            }
        }


        // ==========================================
        // 5. SUMMARY
        // ==========================================

        statistics.setTotalCompetencies(
                totalCompetencies
        );


        statistics.setValidatedCompetencies(
                validatedCompetencies
        );


        statistics.setPendingCompetencies(
                pendingCompetencies
        );


        // ==========================================
        // AVERAGE LEVEL
        // ==========================================

        double averageLevel = 0;


        if (levelCount > 0) {

            averageLevel =
                    totalLevels
                            /
                            levelCount;
        }


        statistics.setAverageLevel(
                round(averageLevel)
        );


        // ==========================================
        // AVERAGE PERCENTAGE
        // ==========================================

        double averagePercentage = 0;


        if (!employees.isEmpty()) {

            averagePercentage =
                    totalPercentage
                            /
                            employees.size();
        }


        statistics.setAveragePercentage(
                round(averagePercentage)
        );


        // ==========================================
        // LEVEL DISTRIBUTION
        // ==========================================

        statistics.setLevel0(level0);

        statistics.setLevel1(level1);

        statistics.setLevel2(level2);

        statistics.setLevel3(level3);

        statistics.setLevel4(level4);


        // ==========================================
        // 6. POSITION STATISTICS
        // ==========================================

        List<StatisticsDTO.PositionStatisticsDTO>
                positions =
                new ArrayList<>();


        for (String position :
                positionPercentages.keySet()) {

            List<Double> percentages =
                    positionPercentages
                            .get(position);


            List<Double> levels =
                    positionLevels
                            .get(position);


            double averagePositionPercentage =
                    average(percentages);


            double averagePositionLevel =
                    average(levels);


            int employeeCount =
                    percentages.size();


            positions.add(
                    new StatisticsDTO
                            .PositionStatisticsDTO(
                            position,
                            employeeCount,
                            round(
                                    averagePositionPercentage
                            ),
                            round(
                                    averagePositionLevel
                            )
                    )
            );
        }


        statistics.setPositions(
                positions
        );


        // ==========================================
        // 7. COMPETENCY STATISTICS
        // ==========================================

        statistics.setCompetencies(
                buildCompetencyStatistics(
                        employees
                )
        );


        // ==========================================
        // 8. EMPLOYEE STATISTICS
        // ==========================================

        statistics.setEmployees(
                employeeStatistics
        );


        return statistics;
    }


    // =====================================================
    // COMPETENCY STATISTICS
    // =====================================================

    private List<StatisticsDTO.CompetencyStatisticsDTO>
    buildCompetencyStatistics(
            List<Utilisateur> employees) {


        Map<Long, CompetencyAccumulator>
                competencyMap =
                new HashMap<>();


        // ==========================================
        // PROCESS EMPLOYEES
        // ==========================================

        for (Utilisateur employee :
                employees) {

            String poste =
                    employee.getPoste();


            if (poste == null ||
                    poste.isBlank()) {

                continue;
            }


            List<PosteCompetence>
                    requiredCompetencies =
                    posteCompetenceRepository
                            .findByPoste(poste);


            List<UtilisateurCompetence>
                    evaluations =
                    utilisateurCompetenceRepository
                            .findByUtilisateurId(
                                    employee.getId()
                            );


            // ======================================
            // PROCESS REQUIRED COMPETENCIES
            // ======================================

            for (PosteCompetence pc :
                    requiredCompetencies) {

                if (pc.getCompetence() == null) {
                    continue;
                }


                if (pc.getNiveauRequis() == null) {
                    continue;
                }


                Competence competence =
                        pc.getCompetence();


                Long competenceId =
                        competence.getId();


                CompetencyAccumulator accumulator =
                        competencyMap
                                .computeIfAbsent(
                                        competenceId,
                                        k ->
                                                new CompetencyAccumulator(
                                                        competenceId,
                                                        competence.getNom(),
                                                        pc.getNiveauRequis()
                                                )
                                );


                // ----------------------------------
                // Find evaluation
                // ----------------------------------

                UtilisateurCompetence evaluation =
                        evaluations.stream()
                                .filter(e ->
                                        e.getCompetence() != null
                                                &&
                                                e.getCompetence()
                                                        .getId()
                                                        .equals(
                                                                competenceId
                                                        )
                                )
                                .findFirst()
                                .orElse(null);


                // ----------------------------------
                // Employee declared level
                // ----------------------------------

                if (evaluation != null &&
                        evaluation.getNiveauEmploye()
                                != null) {

                    accumulator
                            .employeeLevelTotal +=
                            Math.min(
                                    evaluation
                                            .getNiveauEmploye(),
                                    pc.getNiveauRequis()
                            );
                }


                // ----------------------------------
                // Validated level
                // ----------------------------------

                if (evaluation != null &&
                        evaluation.getStatut()
                                == StatutEvaluation.VALIDE
                        &&
                        evaluation.getNiveauValide()
                                != null) {

                    accumulator
                            .validatedLevelTotal +=
                            Math.min(
                                    evaluation
                                            .getNiveauValide(),
                                    pc.getNiveauRequis()
                            );

                    accumulator
                            .validatedCount++;
                }


                accumulator.employeeCount++;
            }
        }


        // ==========================================
        // BUILD RESULT
        // ==========================================

        List<StatisticsDTO.CompetencyStatisticsDTO>
                result =
                new ArrayList<>();


        for (CompetencyAccumulator accumulator :
                competencyMap.values()) {


            double averageEmployeeLevel = 0;

            double averageValidatedLevel = 0;

            double achievementPercentage = 0;


            if (accumulator.employeeCount > 0) {

                averageEmployeeLevel =
                        accumulator.employeeLevelTotal
                                /
                                accumulator.employeeCount;


                achievementPercentage =
                        (
                                averageEmployeeLevel
                                        /
                                        accumulator.requiredLevel
                        )
                                * 100;
            }


            if (accumulator.validatedCount > 0) {

                averageValidatedLevel =
                        accumulator.validatedLevelTotal
                                /
                                accumulator.validatedCount;
            }


            result.add(
                    new StatisticsDTO
                            .CompetencyStatisticsDTO(
                            accumulator.competenceId,
                            accumulator.competence,
                            accumulator.requiredLevel,
                            round(
                                    averageEmployeeLevel
                            ),
                            round(
                                    averageValidatedLevel
                            ),
                            round(
                                    achievementPercentage
                            )
                    )
            );
        }


        return result;
    }


    // =====================================================
    // ACCUMULATOR
    // =====================================================

    private static class CompetencyAccumulator {

        Long competenceId;

        String competence;

        int requiredLevel;

        double employeeLevelTotal = 0;

        double validatedLevelTotal = 0;

        int employeeCount = 0;

        int validatedCount = 0;


        CompetencyAccumulator(
                Long competenceId,
                String competence,
                int requiredLevel) {

            this.competenceId =
                    competenceId;

            this.competence =
                    competence;

            this.requiredLevel =
                    requiredLevel;
        }
    }


    // =====================================================
    // AVERAGE
    // =====================================================

    private double average(
            List<Double> values) {

        if (values == null ||
                values.isEmpty()) {

            return 0;
        }


        double total = 0;


        for (Double value : values) {

            if (value != null) {
                total += value;
            }
        }


        return total / values.size();
    }


    // =====================================================
    // ROUND
    // =====================================================

    private double round(double value) {

        return Math.round(
                value * 100.0
        ) / 100.0;
    }
}