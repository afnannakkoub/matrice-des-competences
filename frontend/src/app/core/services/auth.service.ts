import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

import { environment } from '../../../environments/environment';


// =====================================================
// LOGIN REQUEST
// =====================================================

export interface LoginRequest {

  email: string;

  motDePasse: string;

}


// =====================================================
// LOGIN RESPONSE
// =====================================================

export interface LoginResponse {

  id: number;

  nom: string;

  prenom: string;

  email: string;

  role: string;

  poste: string;

  departement: string;

}


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private http = inject(HttpClient);

  private api = environment.apiUrl;

  private readonly USER_KEY = 'currentUser';


  // =====================================================
  // LOGIN
  // =====================================================

  login(
    email: string,
    motDePasse: string
  ): Observable<LoginResponse> {

    const request: LoginRequest = {

      email: email,

      motDePasse: motDePasse

    };


    return this.http
      .post<LoginResponse>(
        `${this.api}/auth/login`,
        request
      )
      .pipe(

        tap(user => {

          localStorage.setItem(
            this.USER_KEY,
            JSON.stringify(user)
          );

        })

      );
  }


  // =====================================================
  // LOGOUT
  // =====================================================

  logout(): void {

    localStorage.removeItem(
      this.USER_KEY
    );

  }


  // =====================================================
  // CURRENT USER
  // =====================================================

  getCurrentUser(): LoginResponse | null {

    const user =
      localStorage.getItem(this.USER_KEY);


    if (!user) {
      return null;
    }


    try {

      return JSON.parse(user) as LoginResponse;

    } catch {

      this.logout();

      return null;

    }
  }


  // =====================================================
  // AUTHENTICATED?
  // =====================================================

  isLoggedIn(): boolean {

    return this.getCurrentUser() !== null;

  }


  // =====================================================
  // USER ID
  // =====================================================

  getUserId(): number | null {

    return this.getCurrentUser()?.id ?? null;

  }


  // =====================================================
  // ROLE
  // =====================================================

  getRole(): string | null {

    return this.getCurrentUser()?.role ?? null;

  }


  // =====================================================
  // MANAGER?
  // =====================================================

  isManager(): boolean {

    return this.getRole() === 'MANAGER';

  }


  // =====================================================
  // EMPLOYEE?
  // =====================================================

  isEmployee(): boolean {

    return this.getRole() === 'EMPLOYE';

  }


  // =====================================================
  // ADMIN?
  // =====================================================

  isAdmin(): boolean {

    return this.getRole() === 'ADMIN';

  }

}