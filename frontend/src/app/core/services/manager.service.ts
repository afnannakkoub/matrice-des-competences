import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';

import { ManagerDashboard } from '../models/manager-dashboard';
import { EmployeeSkillMatrix } from '../models/employee-skill-matrix';
import { ValidationMatrixRow } from '../models/validation-matrix';

@Injectable({
  providedIn: 'root'
})
export class ManagerService {

  private http = inject(HttpClient);

  private api = environment.apiUrl;


  // =====================================================
  // DASHBOARD
  // =====================================================

  getDashboard(
    managerId: number
  ): Observable<ManagerDashboard> {

    return this.http.get<ManagerDashboard>(
      `${this.api}/utilisateurs/manager/${managerId}/dashboard`
    );

  }


  // =====================================================
  // GET TEAM
  // =====================================================

  getTeam(managerId: number) {

    return this.http.get<any[]>(
      `${this.api}/utilisateurs/manager/${managerId}/equipe`
    );

  }


  // =====================================================
  // TEAM SKILL MATRIX
  // =====================================================

  getSkillMatrix(managerId: number) {

    return this.http.get<any[]>(
      `${this.api}/utilisateurs/manager/${managerId}/matrix`
    );

  }


  // =====================================================
  // EMPLOYEE SKILL MATRIX
  // =====================================================

  getEmployeeSkillMatrix(employeeId: number) {

    return this.http.get<EmployeeSkillMatrix[]>(
      `${this.api}/utilisateurs/${employeeId}/skill-matrix`
    );

  }


  // =====================================================
  // MANAGER EVALUATIONS
  // =====================================================

  getManagerEvaluations(employeeId: number) {

    return this.http.get<any[]>(
      `${this.api}/utilisateur-competences/manager/evaluate/${employeeId}`
    );

  }


  // =====================================================
  // VALIDATE COMPETENCY
  // =====================================================

  validateCompetency(
    evaluationId: number,
    niveau: number,
    managerId: number
  ) {

    return this.http.put(
      `${this.api}/utilisateur-competences/${evaluationId}/valider`,
      null,
      {
        params: {
          niveau: niveau,
          managerId: managerId
        }
      }
    );

  }


  // =====================================================
  // MANAGER DIRECT EVALUATION
  // =====================================================

  managerEvaluate(
    utilisateurId: number,
    competenceId: number,
    niveau: number,
    managerId: number
  ) {

    return this.http.post<any>(
      `${this.api}/utilisateur-competences/manager/evaluate`,
      {
        utilisateurId: utilisateurId,
        competenceId: competenceId,
        niveau: niveau,
        managerId: managerId
      }
    );

  }


  // =====================================================
  // VALIDATION MATRIX
  // =====================================================

  getValidationMatrix(managerId: number) {

    return this.http.get<any[]>(
      `${this.api}/utilisateurs/manager/${managerId}/validation-matrix`
    );

  }


  // =====================================================
  // COMPETENCIES
  // =====================================================

  getCompetences() {

    return this.http.get<any[]>(
      `${this.api}/competences`
    );

  }


  // =====================================================
  // ADD EMPLOYEE COMPETENCY
  // =====================================================

  addEmployeeCompetence(data: any) {

    return this.http.post<any>(
      `${this.api}/utilisateur-competences`,
      data
    );

  }

  getEmployeeCompetencies(employeeId: number) {

  return this.http.get<any[]>(
    `${this.api}/utilisateur-competences/utilisateur/${employeeId}`
  );

}

  // =====================================================
  // STATISTICS
  // =====================================================

  getStatistics(
    managerId: number
  ): Observable<StatisticsDTO> {

    return this.http.get<StatisticsDTO>(
      `${this.api}/statistics/manager/${managerId}`
    );

  }

  getEmployee(employeeId: number) {

  return this.http.get<any>(
    `${this.api}/utilisateurs/${employeeId}`
  );

}

}

export interface StatisticsDTO {

  totalEmployees: number;

  totalCompetencies: number;

  validatedCompetencies: number;

  pendingCompetencies: number;

  averageLevel: number;

  averagePercentage: number;

  level0: number;

  level1: number;

  level2: number;

  level3: number;

  level4: number;

  positions: PositionStatisticsDTO[];

  competencies: CompetencyStatisticsDTO[];

  employees: EmployeeStatisticsDTO[];
}


export interface PositionStatisticsDTO {

  poste: string;

  employeeCount: number;

  averagePercentage: number;

  averageLevel: number;
}


export interface CompetencyStatisticsDTO {

  competenceId: number;

  competence: string;

  requiredLevel: number;

  averageEmployeeLevel: number;

  averageValidatedLevel: number;

  achievementPercentage: number;
}


export interface EmployeeStatisticsDTO {

  utilisateurId: number;

  nom: string;

  prenom: string;

  poste: string;

  averageLevel: number;

  percentage: number;

  validatedCompetencies: number;

  totalCompetencies: number;
}