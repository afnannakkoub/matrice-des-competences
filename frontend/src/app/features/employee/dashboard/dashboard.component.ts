import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { ManagerService } from '../../../core/services/manager.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class EmployeeDashboardComponent implements OnInit {

  private managerService = inject(ManagerService);
  private authService = inject(AuthService);
  private router = inject(Router);

  employee: any = null;

  competencies: any[] = [];

  employeeId: number | null = null;

  loading = true;

  error = '';

  // ==========================================
  // INIT
  // ==========================================

  ngOnInit(): void {

    const user = this.authService.getCurrentUser();

    // ==========================================
    // CHECK AUTHENTICATION
    // ==========================================

    if (!user) {

      this.error =
        'No authenticated user found.';

      this.loading = false;

      this.router.navigate(['/login']);

      return;
    }

    // ==========================================
    // CHECK ROLE
    // ==========================================

    if (user.role !== 'EMPLOYEE') {

      this.error =
        'Access denied.';

      this.loading = false;

      return;
    }

    // ==========================================
    // GET LOGGED-IN EMPLOYEE ID
    // ==========================================

    this.employeeId = user.id;

    console.log(
      'Logged-in employee:',
      user
    );

    console.log(
      'Employee ID:',
      this.employeeId
    );

    this.loadEmployee();

    this.loadCompetencies();

  }

  // ==========================================
  // LOAD EMPLOYEE
  // ==========================================

  loadEmployee(): void {

    if (this.employeeId === null) {
      return;
    }

    this.managerService
      .getEmployee(this.employeeId)
      .subscribe({

        next: employee => {

          console.log(
            'Employee:',
            employee
          );

          this.employee = employee;

        },

        error: err => {

          console.error(
            'Employee loading error:',
            err
          );

          this.error =
            'Unable to load employee information.';

        }

      });

  }

  // ==========================================
  // LOAD COMPETENCIES
  // ==========================================

  loadCompetencies(): void {

    if (this.employeeId === null) {
      return;
    }

    this.managerService
      .getEmployeeCompetencies(
        this.employeeId
      )
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

          console.error(
            'Competencies loading error:',
            err
          );

          this.error =
            'Unable to load competencies.';

          this.loading = false;

        }

      });

  }

  // ==========================================
  // STATISTICS
  // ==========================================

  getTotalCompetencies(): number {

    return this.competencies.length;

  }

  getValidatedCompetencies(): number {

    return this.competencies.filter(
      c => c.statut === 'VALIDE'
    ).length;

  }

  getPendingCompetencies(): number {

    return this.competencies.filter(
      c => c.statut === 'EN_ATTENTE'
    ).length;

  }

  // ==========================================
  // NAVIGATION
  // ==========================================

  openCompetencies(): void {

    this.router.navigate([
      '/employee/competencies'
    ]);

  }

  openMatrix(): void {

    this.router.navigate([
      '/employee/skill-matrix'
    ]);

  }

}