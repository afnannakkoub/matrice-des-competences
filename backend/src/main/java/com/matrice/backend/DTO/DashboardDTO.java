package com.matrice.backend.DTO;

public class DashboardDTO {

    private long totalUsers;
    private long totalManagers;
    private long totalEmployees;
    private long totalCompetences;
    private long totalCategories;
    private long pendingValidations;

    public DashboardDTO() {
    }

    public DashboardDTO(long totalUsers,
                        long totalManagers,
                        long totalEmployees,
                        long totalCompetences,
                        long totalCategories,
                        long pendingValidations) {

        this.totalUsers = totalUsers;
        this.totalManagers = totalManagers;
        this.totalEmployees = totalEmployees;
        this.totalCompetences = totalCompetences;
        this.totalCategories = totalCategories;
        this.pendingValidations = pendingValidations;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalManagers() {
        return totalManagers;
    }

    public void setTotalManagers(long totalManagers) {
        this.totalManagers = totalManagers;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getTotalCompetences() {
        return totalCompetences;
    }

    public void setTotalCompetences(long totalCompetences) {
        this.totalCompetences = totalCompetences;
    }

    public long getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(long totalCategories) {
        this.totalCategories = totalCategories;
    }

    public long getPendingValidations() {
        return pendingValidations;
    }

    public void setPendingValidations(long pendingValidations) {
        this.pendingValidations = pendingValidations;
    }
}
