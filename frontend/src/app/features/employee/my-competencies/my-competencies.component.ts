import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import {
  RouterLink,
  RouterLinkActive
} from '@angular/router';

import { ManagerService } from '../../../core/services/manager.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-my-competencies',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './my-competencies.component.html',
  styleUrl: './my-competencies.component.css'
})
export class MyCompetenciesComponent implements OnInit {

  private managerService = inject(ManagerService);
  private authService = inject(AuthService);
  private router = inject(Router);

  // ==========================================
  // LOGGED-IN EMPLOYEE
  // ==========================================

  employeeId!: number;

  competencies: any[] = [];

  loading = true;

  error = '';


  // ==========================================
  // INIT
  // ==========================================

  ngOnInit(): void {

    const userId = this.authService.getUserId();

    if (!userId) {

      console.error(
        'No logged-in user found.'
      );

      this.error =
        'Unable to identify the logged-in user.';

      this.loading = false;

      return;
    }

    this.employeeId = userId;

    console.log(
      'Logged-in employee ID:',
      this.employeeId
    );

    this.loadCompetencies();
  }


  // ==========================================
  // LOAD EMPLOYEE COMPETENCIES
  // ==========================================

  loadCompetencies(): void {

    this.loading = true;

    this.managerService
      .getEmployeeCompetencies(this.employeeId)
      .subscribe({

        next: data => {

          console.log(
            'Employee competencies:',
            data
          );

          this.competencies = data;

          this.loading = false;
        },

        error: err => {

          console.error(err);

          this.error =
            'Unable to load your competencies.';

          this.loading = false;
        }

      });
  }


  // ==========================================
  // STATUS
  // ==========================================

  getStatusClass(
    status: string
  ): string {

    switch (status) {

      case 'VALIDE':
        return 'bg-success';

      case 'EN_ATTENTE':
        return 'bg-warning text-dark';

      case 'REFUSE':
        return 'bg-danger';

      default:
        return 'bg-secondary';
    }
  }


  // ==========================================
  // LEVEL
  // ==========================================

  getLevelLabel(
    level: number | null
  ): string {

    if (
      level === null ||
      level === undefined
    ) {

      return 'Not evaluated';
    }

    switch (level) {

      case 0:
        return 'Beginner';

      case 1:
        return 'Basic';

      case 2:
        return 'Intermediate';

      case 3:
        return 'Advanced';

      case 4:
        return 'Expert';

      default:
        return 'Unknown';
    }
  }


  // ==========================================
  // BACK TO DASHBOARD
  // ==========================================

  goDashboard(): void {

    this.router.navigate([
      '/employee/dashboard'
    ]);
  }

}