import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ManagerService } from '../../../core/services/manager.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-evaluate',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './evaluate.component.html',
  styleUrl: './evaluate.component.css'
})
export class EvaluateComponent implements OnInit {

  private managerService = inject(ManagerService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private authService = inject(AuthService);


  // ==========================================
  // IDS
  // ==========================================

  employeeId!: number;

  managerId!: number;


  // ==========================================
  // DATA
  // ==========================================

  employee: any = null;

  evaluations: any[] = [];


  // ==========================================
  // STATE
  // ==========================================

  loading = true;

  error = '';


  // ==========================================
  // INIT
  // ==========================================

  ngOnInit(): void {

    // ----------------------------------------
    // Get employee ID from URL
    // ----------------------------------------

    const id =
      this.route.snapshot.paramMap.get('id');

    if (!id) {

      this.error = 'Employee ID is missing.';

      this.loading = false;

      return;
    }

    this.employeeId = Number(id);


    // ----------------------------------------
    // Get logged-in user
    // ----------------------------------------

    const user =
      this.authService.getCurrentUser();


    if (!user) {

      this.error =
        'No authenticated user found.';

      this.loading = false;

      this.router.navigate(['/login']);

      return;
    }


    // ----------------------------------------
    // Check role
    // ----------------------------------------

    if (user.role !== 'MANAGER') {

      this.error =
        'You are not authorized to evaluate employees.';

      this.loading = false;

      this.router.navigate([
        '/employee/dashboard'
      ]);

      return;
    }


    // ----------------------------------------
    // Manager ID
    // ----------------------------------------

    this.managerId = user.id;


    console.log(
      'Employee ID:',
      this.employeeId
    );

    console.log(
      'Manager ID:',
      this.managerId
    );


    // ----------------------------------------
    // Load data
    // ----------------------------------------

    this.loadEmployee();

    this.loadEvaluations();

  }


  // ==========================================
  // LOAD EMPLOYEE
  // ==========================================

  loadEmployee(): void {

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
            'Unable to load employee.';

        }

      });

  }


  // ==========================================
  // LOAD EMPLOYEE COMPETENCIES
  // ==========================================

  loadEvaluations(): void {

    this.managerService
      .getManagerEvaluations(
        this.employeeId
      )
      .subscribe({

        next: data => {

          console.log(
            'Employee evaluations:',
            data
          );

          this.evaluations = data;

          this.loading = false;

        },

        error: err => {

          console.error(
            'Evaluation loading error:',
            err
          );

          this.error =
            'Unable to load employee competencies.';

          this.loading = false;

        }

      });

  }


  // ==========================================
  // VALIDATE / EVALUATE
  // ==========================================

  validate(evaluation: any): void {

    if (
      evaluation.niveauValide === null ||
      evaluation.niveauValide === undefined
    ) {

      alert(
        'Please select a level.'
      );

      return;
    }


    this.managerService
      .managerEvaluate(

        this.employeeId,

        evaluation.competenceId,

        evaluation.niveauValide,

        this.managerId

      )
      .subscribe({

        next: response => {

          console.log(
            'Evaluation saved:',
            response
          );


          // ----------------------------------
          // Update local data
          // ----------------------------------

          evaluation.evaluationId =
            response.id;

          evaluation.niveauEmploye =
            response.niveauEmploye;

          evaluation.niveauValide =
            response.niveauValide;

          evaluation.statut =
            response.statut;


          alert(
            'Competency evaluated successfully.'
          );

        },

        error: err => {

          console.error(
            'Evaluation error:',
            err
          );

          alert(
            'Error while evaluating competency.'
          );

        }

      });

  }


  // ==========================================
  // BACK
  // ==========================================

  goBack(): void {

    this.router.navigate([
      '/manager/team'
    ]);

  }

}