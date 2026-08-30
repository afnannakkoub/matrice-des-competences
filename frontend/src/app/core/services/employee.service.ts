import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private http = inject(HttpClient);

  private api = environment.apiUrl;

  // ==========================================
  // MY SKILL MATRIX
  // ==========================================

  getMySkillMatrix(employeeId: number): Observable<any[]> {

    return this.http.get<any[]>(
      `${this.api}/utilisateurs/${employeeId}/skill-matrix`
    );
  }

  // ==========================================
  // GET ALL COMPETENCIES
  // ==========================================

  getCompetences(): Observable<any[]> {

    return this.http.get<any[]>(
      `${this.api}/competences`
    );
  }

  // ==========================================
  // EMPLOYEE SELF-EVALUATION
  // ==========================================

  addCompetence(
    employeeId: number,
    competenceId: number,
    niveau: number
  ): Observable<any> {

    const data = {

      utilisateur: {
        id: employeeId
      },

      competence: {
        id: competenceId
      },

      niveauEmploye: niveau

    };

    return this.http.post<any>(
      `${this.api}/utilisateur-competences`,
      data
    );
  }

  // ==========================================
  // UPDATE SELF-EVALUATION
  // ==========================================

  updateCompetence(
    evaluationId: number,
    niveau: number
  ): Observable<any> {

    const data = {

      niveauEmploye: niveau

    };

    return this.http.put<any>(
      `${this.api}/utilisateur-competences/${evaluationId}`,
      data
    );
  }

// ==========================================
// MY PROFILE
// ==========================================

getProfile(employeeId: number): Observable<any> {

  return this.http.get<any>(
    `${this.api}/utilisateurs/${employeeId}`
  );
}

// ==========================================
// UPDATE MY PROFILE
// ==========================================

updateProfile(
  employeeId: number,
  profile: any
): Observable<any> {

  const data = {
    nom: profile.nom,
    prenom: profile.prenom,
    email: profile.email
  };

  return this.http.put<any>(
    `${this.api}/utilisateurs/${employeeId}/profile`,
    data
  );
}

}