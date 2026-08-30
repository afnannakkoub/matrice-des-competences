import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import {
  RouterLink,
  RouterLinkActive
} from '@angular/router';

import { ManagerService } from '../../../core/services/manager.service';
import { EmployeeService } from '../../../core/services/employee.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-employee-skill-matrix',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    RouterLinkActive
  ],

  templateUrl: './skill-matrix.component.html',
  styleUrl: './skill-matrix.component.css'
})
export class SkillMatrixComponent implements OnInit {

  private employeeService = inject(EmployeeService);
  private authService = inject(AuthService);
  private router = inject(Router);


  // ==========================================
  // LOGGED-IN EMPLOYEE
  // ==========================================

  employeeId!: number;


  // ==========================================
  // EMPLOYEE
  // ==========================================

  employee: any = null;


  // ==========================================
  // SKILL MATRIX
  // ==========================================

  matrix: any[] = [];


  loading = true;

  error = '';


  // ==========================================
  // ADD COMPETENCE
  // ==========================================

  showAddForm = false;

  availableCompetences: any[] = [];

  selectedCompetenceId: number | null = null;

  selfLevel: number | null = null;


  // ==========================================
  // INIT
  // ==========================================

  ngOnInit(): void {

    const userId =
      this.authService.getUserId();

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

    this.loadEmployee();

    this.loadSkillMatrix();

    this.loadAvailableCompetences();
  }


  // ==========================================
  // LOAD EMPLOYEE
  // ==========================================

  loadEmployee(): void {

    this.employeeService
      .getProfile(this.employeeId)
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
            'Unable to load employee:',
            err
          );
        }

      });
  }


  // ==========================================
  // LOAD SKILL MATRIX
  // ==========================================

  loadSkillMatrix(): void {

    this.loading = true;

    this.employeeService
      .getMySkillMatrix(this.employeeId)
      .subscribe({

        next: data => {

          console.log(
            'Employee Skill Matrix:',
            data
          );

          this.matrix = data;

          this.loading = false;
        },

        error: err => {

          console.error(err);

          this.error =
            'Unable to load your skill matrix.';

          this.loading = false;
        }

      });
  }


  // ==========================================
  // LOAD AVAILABLE COMPETENCES
  // ==========================================

  loadAvailableCompetences(): void {

    this.employeeService
      .getCompetences()
      .subscribe({

        next: data => {

          console.log(
            'Available competencies:',
            data
          );

          this.availableCompetences = data;
        },

        error: err => {

          console.error(
            'Unable to load competencies:',
            err
          );
        }

      });
  }


  // ==========================================
  // OPEN ADD FORM
  // ==========================================

  openAddForm(): void {

    this.showAddForm = true;

    this.selectedCompetenceId = null;

    this.selfLevel = null;
  }


  // ==========================================
  // CANCEL ADD
  // ==========================================

  cancelAdd(): void {

    this.showAddForm = false;

    this.selectedCompetenceId = null;

    this.selfLevel = null;
  }


  // ==========================================
  // ADD COMPETENCE
  // ==========================================

  addCompetence(): void {

    if (
      this.selectedCompetenceId === null ||
      this.selfLevel === null
    ) {

      alert(
        'Please select a competency and your level.'
      );

      return;
    }

    this.employeeService
      .addCompetence(
        this.employeeId,
        this.selectedCompetenceId,
        this.selfLevel
      )
      .subscribe({

        next: response => {

          console.log(
            'Competency added:',
            response
          );

          alert(
            'Competency added successfully. Waiting for manager validation.'
          );

          this.showAddForm = false;

          this.selectedCompetenceId = null;

          this.selfLevel = null;

          this.loadSkillMatrix();
        },

        error: err => {

          console.error(
            'Error adding competency:',
            err
          );

          if (err.error?.message) {

            alert(
              err.error.message
            );

          } else {

            alert(
              'Error while adding competency.'
            );
          }
        }

      });
  }


  // ==========================================
  // UPDATE SELF EVALUATION
  // ==========================================

  updateSelfEvaluation(
    skill: any
  ): void {

    if (
      skill.niveauActuel === null ||
      skill.niveauActuel === undefined
    ) {

      alert(
        'Please select your level.'
      );

      return;
    }

    if (!skill.evaluationId) {

      alert(
        'This competency has not been declared yet.'
      );

      return;
    }

    this.employeeService
      .updateCompetence(
        skill.evaluationId,
        skill.niveauActuel
      )
      .subscribe({

        next: () => {

          alert(
            'Your self-evaluation has been updated. Waiting for manager validation.'
          );

          this.loadSkillMatrix();
        },

        error: err => {

          console.error(err);

          alert(
            'Error while updating your evaluation.'
          );
        }

      });
  }


  // ==========================================
  // LEVEL LABEL
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
  // GAP CLASS
  // ==========================================

  getGapClass(
    gap: number | null
  ): string {

    if (
      gap === null ||
      gap === undefined
    ) {

      return 'gap-none';
    }

    if (gap === 0) {

      return 'gap-ok';
    }

    if (gap === 1) {

      return 'gap-low';
    }

    if (gap === 2) {

      return 'gap-medium';
    }

    return 'gap-high';
  }


  // ==========================================
  // GAP LABEL
  // ==========================================

  getGapLabel(
    gap: number | null
  ): string {

    if (
      gap === null ||
      gap === undefined
    ) {

      return '—';
    }

    if (gap === 0) {

      return 'No gap';
    }

    if (gap === 1) {

      return '1 level below';
    }

    return `${gap} levels below`;
  }


  // ==========================================
  // STATUS CLASS
  // ==========================================

  getStatusClass(
    status: string
  ): string {

    switch (status) {

      case 'VALIDE':
        return 'bg-success';

      case 'EN_ATTENTE':
        return 'bg-warning text-dark';

      case 'NON_EVALUE':
        return 'bg-secondary';

      default:
        return 'bg-secondary';
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