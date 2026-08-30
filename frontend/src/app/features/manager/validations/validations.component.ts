import {
  Component,
  OnInit,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';

import { ManagerService } from '../../../core/services/manager.service';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-validations',

  standalone: true,

  imports: [
    CommonModule
  ],

  templateUrl: './validations.component.html',

  styleUrl: './validations.component.css'
})
export class ValidationsComponent implements OnInit {

  private managerService =
    inject(ManagerService);

  private authService =
    inject(AuthService);

  private router =
    inject(Router);


  // ==========================================
  // MANAGER
  // ==========================================

  managerId!: number;


  // ==========================================
  // DATA
  // ==========================================

  employees: any[] = [];

  posts: any[] = [];


  // ==========================================
  // STATE
  // ==========================================

  loading = true;

  error = '';


  // ==========================================
  // INIT
  // ==========================================

  ngOnInit(): void {

    const user =
      this.authService.getCurrentUser();


    // ------------------------------------------
    // No authenticated user
    // ------------------------------------------

    if (!user) {

      this.error =
        'No authenticated user found.';

      this.loading = false;

      this.router.navigate([
        '/login'
      ]);

      return;
    }


    // ------------------------------------------
    // Check manager role
    // ------------------------------------------

    if (user.role !== 'MANAGER') {

      this.error =
        'You are not authorized to access validations.';

      this.loading = false;

      return;
    }


    // ------------------------------------------
    // Get manager ID
    // ------------------------------------------

    this.managerId = user.id;


    console.log(
      'Logged-in manager:',
      user
    );

    console.log(
      'Logged-in manager ID:',
      this.managerId
    );


    // ------------------------------------------
    // Load data
    // ------------------------------------------

    this.loadMatrix();

  }


  // ==========================================
  // LOAD TEAM + VALIDATION MATRIX
  // ==========================================

  loadMatrix(): void {

    this.loading = true;

    this.error = '';


    this.managerService
      .getTeam(this.managerId)
      .subscribe({

        next: employees => {

          console.log(
            'Manager team:',
            employees
          );

          this.employees = employees;

          this.loadValidationMatrix();

        },

        error: err => {

          console.error(
            'Error loading team:',
            err
          );

          this.error =
            'Unable to load team employees.';

          this.loading = false;

        }

      });

  }


  // ==========================================
  // LOAD VALIDATION MATRIX
  // ==========================================

  loadValidationMatrix(): void {

    this.managerService
      .getValidationMatrix(this.managerId)
      .subscribe({

        next: data => {

          console.log(
            'Validation matrix:',
            data
          );

          this.posts = data;

          this.loading = false;

        },

        error: err => {

          console.error(
            'Error loading validation matrix:',
            err
          );

          this.error =
            'Unable to load validation matrix.';

          this.loading = false;

        }

      });

  }


  // ==========================================
  // GET EMPLOYEE LEVEL
  // ==========================================

  getLevel(
    post: any,
    employee: any
  ): number {

    return post.niveaux?.[employee.id] ?? 0;

  }


  // ==========================================
  // GET PERCENTAGE
  // ==========================================

  getPercentage(
    post: any,
    employee: any
  ): number {

    return post.pourcentages?.[employee.id] ?? 0;

  }


  // ==========================================
  // INITIALS
  // ==========================================

  getInitials(
    employee: any
  ): string {

    return (
      (employee.prenom?.charAt(0) || '') +
      (employee.nom?.charAt(0) || '')
    ).toUpperCase();

  }


  // ==========================================
  // LEVEL CLASS
  // ==========================================

  getLevelClass(
    level: number
  ): string {

    switch (level) {

      case 1:
        return 'level-1';

      case 2:
        return 'level-2';

      case 3:
        return 'level-3';

      case 4:
        return 'level-4';

      default:
        return 'level-0';

    }

  }


  // ==========================================
  // GET COMPETENCY DETAILS
  // ==========================================

  getDetails(
    post: any,
    employee: any
  ): any[] {

    return post.details?.[employee.id] ?? [];

  }


  // ==========================================
  // COMPETENCY TOOLTIP
  // ==========================================

  getCompetencyTooltip(
    post: any,
    employee: any
  ): string {

    const details =
      this.getDetails(
        post,
        employee
      );


    if (
      !details ||
      details.length === 0
    ) {

      return 'No competency details available.';

    }


    return details
      .map(skill => {

        const required =
          skill.niveauRequis
          ?? '-';


        const employeeLevel =
          skill.niveauEmploye
          ?? '-';


        const validated =
          skill.niveauValide
          ?? '-';


        const status =
          skill.statut
          ?? 'NON_EVALUE';


        return (
          `${skill.competence}: ` +
          `Required ${required} | ` +
          `Employee ${employeeLevel} | ` +
          `Validated ${validated} | ` +
          `Status ${status}`
        );

      })
      .join('\n');

  }

}