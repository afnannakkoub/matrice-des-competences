import {
  Component,
  OnInit,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {

  private authService = inject(AuthService);
  private router = inject(Router);

  // ==========================================
  // CURRENT USER
  // ==========================================

  user: any = null;


  // ==========================================
  // INIT
  // ==========================================

  ngOnInit(): void {

    this.loadCurrentUser();

  }


  // ==========================================
  // LOAD USER
  // ==========================================

  loadCurrentUser(): void {

    this.user =
      this.authService.getCurrentUser();

    console.log(
      'Navbar user:',
      this.user
    );

  }


  // ==========================================
  // DISPLAY NAME
  // ==========================================

  getUserName(): string {

    if (!this.user) {
      return 'Guest';
    }

    return `${this.user.prenom} ${this.user.nom}`;

  }


  // ==========================================
  // DISPLAY ROLE
  // ==========================================

  getUserRole(): string {

    if (!this.user) {
      return '';
    }

    switch (this.user.role) {

      case 'ADMIN':
        return 'Administrator';

      case 'MANAGER':
        return 'Manager';

      case 'EMPLOYEE':
        return 'Employee';

      default:
        return this.user.role;

    }

  }


  // ==========================================
  // LOGOUT
  // ==========================================

  logout(): void {

    this.authService.logout();

    this.router.navigate([
      '/login'
    ]);

  }

}