package com.matrice.backend.DTO;

import java.util.List;

public class StatisticsDTO {

    // ==========================================
    // SUMMARY
    // ==========================================

    private long totalEmployees;

    private long totalCompetencies;

    private long validatedCompetencies;

    private long pendingCompetencies;

    private double averageLevel;

    private double averagePercentage;


    // ==========================================
    // LEVEL DISTRIBUTION
    // ==========================================

    private long level0;

    private long level1;

    private long level2;

    private long level3;

    private long level4;


    // ==========================================
    // POSITION STATISTICS
    // ==========================================

    private List<PositionStatisticsDTO> positions;


    // ==========================================
    // COMPETENCY STATISTICS
    // ==========================================

    private List<CompetencyStatisticsDTO> competencies;


    // ==========================================
    // EMPLOYEE STATISTICS
    // ==========================================

    private List<EmployeeStatisticsDTO> employees;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public StatisticsDTO() {
    }


    // ==========================================
    // GETTERS / SETTERS
    // ==========================================

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getTotalCompetencies() {
        return totalCompetencies;
    }

    public void setTotalCompetencies(long totalCompetencies) {
        this.totalCompetencies = totalCompetencies;
    }

    public long getValidatedCompetencies() {
        return validatedCompetencies;
    }

    public void setValidatedCompetencies(long validatedCompetencies) {
        this.validatedCompetencies = validatedCompetencies;
    }

    public long getPendingCompetencies() {
        return pendingCompetencies;
    }

    public void setPendingCompetencies(long pendingCompetencies) {
        this.pendingCompetencies = pendingCompetencies;
    }

    public double getAverageLevel() {
        return averageLevel;
    }

    public void setAverageLevel(double averageLevel) {
        this.averageLevel = averageLevel;
    }

    public double getAveragePercentage() {
        return averagePercentage;
    }

    public void setAveragePercentage(double averagePercentage) {
        this.averagePercentage = averagePercentage;
    }

    public long getLevel0() {
        return level0;
    }

    public void setLevel0(long level0) {
        this.level0 = level0;
    }

    public long getLevel1() {
        return level1;
    }

    public void setLevel1(long level1) {
        this.level1 = level1;
    }

    public long getLevel2() {
        return level2;
    }

    public void setLevel2(long level2) {
        this.level2 = level2;
    }

    public long getLevel3() {
        return level3;
    }

    public void setLevel3(long level3) {
        this.level3 = level3;
    }

    public long getLevel4() {
        return level4;
    }

    public void setLevel4(long level4) {
        this.level4 = level4;
    }

    public List<PositionStatisticsDTO> getPositions() {
        return positions;
    }

    public void setPositions(
            List<PositionStatisticsDTO> positions) {
        this.positions = positions;
    }

    public List<CompetencyStatisticsDTO> getCompetencies() {
        return competencies;
    }

    public void setCompetencies(
            List<CompetencyStatisticsDTO> competencies) {
        this.competencies = competencies;
    }

    public List<EmployeeStatisticsDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(
            List<EmployeeStatisticsDTO> employees) {
        this.employees = employees;
    }


    // ==========================================
    // INNER DTO - POSITION
    // ==========================================

    public static class PositionStatisticsDTO {

        private String poste;

        private int employeeCount;

        private double averagePercentage;

        private double averageLevel;


        public PositionStatisticsDTO() {
        }


        public PositionStatisticsDTO(
                String poste,
                int employeeCount,
                double averagePercentage,
                double averageLevel) {

            this.poste = poste;
            this.employeeCount = employeeCount;
            this.averagePercentage = averagePercentage;
            this.averageLevel = averageLevel;
        }


        public String getPoste() {
            return poste;
        }

        public void setPoste(String poste) {
            this.poste = poste;
        }

        public int getEmployeeCount() {
            return employeeCount;
        }

        public void setEmployeeCount(int employeeCount) {
            this.employeeCount = employeeCount;
        }

        public double getAveragePercentage() {
            return averagePercentage;
        }

        public void setAveragePercentage(
                double averagePercentage) {

            this.averagePercentage = averagePercentage;
        }

        public double getAverageLevel() {
            return averageLevel;
        }

        public void setAverageLevel(double averageLevel) {
            this.averageLevel = averageLevel;
        }
    }


    // ==========================================
    // INNER DTO - COMPETENCY
    // ==========================================

    public static class CompetencyStatisticsDTO {

        private Long competenceId;

        private String competence;

        private int requiredLevel;

        private double averageEmployeeLevel;

        private double averageValidatedLevel;

        private double achievementPercentage;


        public CompetencyStatisticsDTO() {
        }


        public CompetencyStatisticsDTO(
                Long competenceId,
                String competence,
                int requiredLevel,
                double averageEmployeeLevel,
                double averageValidatedLevel,
                double achievementPercentage) {

            this.competenceId = competenceId;
            this.competence = competence;
            this.requiredLevel = requiredLevel;
            this.averageEmployeeLevel =
                    averageEmployeeLevel;
            this.averageValidatedLevel =
                    averageValidatedLevel;
            this.achievementPercentage =
                    achievementPercentage;
        }


        public Long getCompetenceId() {
            return competenceId;
        }

        public void setCompetenceId(Long competenceId) {
            this.competenceId = competenceId;
        }

        public String getCompetence() {
            return competence;
        }

        public void setCompetence(String competence) {
            this.competence = competence;
        }

        public int getRequiredLevel() {
            return requiredLevel;
        }

        public void setRequiredLevel(int requiredLevel) {
            this.requiredLevel = requiredLevel;
        }

        public double getAverageEmployeeLevel() {
            return averageEmployeeLevel;
        }

        public void setAverageEmployeeLevel(
                double averageEmployeeLevel) {

            this.averageEmployeeLevel =
                    averageEmployeeLevel;
        }

        public double getAverageValidatedLevel() {
            return averageValidatedLevel;
        }

        public void setAverageValidatedLevel(
                double averageValidatedLevel) {

            this.averageValidatedLevel =
                    averageValidatedLevel;
        }

        public double getAchievementPercentage() {
            return achievementPercentage;
        }

        public void setAchievementPercentage(
                double achievementPercentage) {

            this.achievementPercentage =
                    achievementPercentage;
        }
    }


    // ==========================================
    // INNER DTO - EMPLOYEE
    // ==========================================

    public static class EmployeeStatisticsDTO {

        private Long utilisateurId;

        private String nom;

        private String prenom;

        private String poste;

        private double averageLevel;

        private double percentage;

        private int validatedCompetencies;

        private int totalCompetencies;


        public EmployeeStatisticsDTO() {
        }


        public EmployeeStatisticsDTO(
                Long utilisateurId,
                String nom,
                String prenom,
                String poste,
                double averageLevel,
                double percentage,
                int validatedCompetencies,
                int totalCompetencies) {

            this.utilisateurId = utilisateurId;
            this.nom = nom;
            this.prenom = prenom;
            this.poste = poste;
            this.averageLevel = averageLevel;
            this.percentage = percentage;
            this.validatedCompetencies =
                    validatedCompetencies;
            this.totalCompetencies =
                    totalCompetencies;
        }


        public Long getUtilisateurId() {
            return utilisateurId;
        }

        public void setUtilisateurId(Long utilisateurId) {
            this.utilisateurId = utilisateurId;
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

        public String getPoste() {
            return poste;
        }

        public void setPoste(String poste) {
            this.poste = poste;
        }

        public double getAverageLevel() {
            return averageLevel;
        }

        public void setAverageLevel(
                double averageLevel) {

            this.averageLevel = averageLevel;
        }

        public double getPercentage() {
            return percentage;
        }

        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }

        public int getValidatedCompetencies() {
            return validatedCompetencies;
        }

        public void setValidatedCompetencies(
                int validatedCompetencies) {

            this.validatedCompetencies =
                    validatedCompetencies;
        }

        public int getTotalCompetencies() {
            return totalCompetencies;
        }

        public void setTotalCompetencies(
                int totalCompetencies) {

            this.totalCompetencies =
                    totalCompetencies;
        }
    }
}