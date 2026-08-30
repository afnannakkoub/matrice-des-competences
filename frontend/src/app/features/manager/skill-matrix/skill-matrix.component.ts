import {
  Component,
  OnInit,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';

import { ManagerService } from '../../../core/services/manager.service';

import { SkillMatrix } from '../../../core/models/skill-matrix';

import { EmployeeSkillMatrix } from '../../../core/models/employee-skill-matrix';

import { AuthService } from '../../../core/services/auth.service';


@Component({
  selector: 'app-skill-matrix',

  standalone: true,

  imports: [
    CommonModule
  ],

  templateUrl: './skill-matrix.component.html',

  styleUrl: './skill-matrix.component.css'
})
export class SkillMatrixComponent implements OnInit {

  private authService =
    inject(AuthService);

  private managerService =
    inject(ManagerService);


  // =========================================
  // IDS
  // =========================================

  managerId!: number;


  // =========================================
  // TEAM MATRIX
  // =========================================

  matrix: SkillMatrix[] = [];

  competencies: string[] = [];


  // =========================================
  // SELECTED EMPLOYEE
  // =========================================

  selectedEmployee:
    SkillMatrix | null = null;


  // =========================================
  // EMPLOYEE DETAIL MATRIX
  // =========================================

  employeeMatrix:
    EmployeeSkillMatrix[] = [];


  // =========================================
  // STATE
  // =========================================

  loading = true;

  error = '';


  // =========================================
  // INIT
  // =========================================

  ngOnInit(): void {

    const user =
      this.authService.getCurrentUser();


    // -----------------------------------------
    // No authenticated user
    // -----------------------------------------

    if (!user) {

      console.error(
        'No authenticated user found.'
      );

      this.error =
        'No authenticated user found.';

      this.loading = false;

      return;
    }


    // -----------------------------------------
    // Check manager role
    // -----------------------------------------

    if (user.role !== 'MANAGER') {

      console.error(
        'User is not a manager.'
      );

      this.error =
        'You are not authorized to access the skill matrix.';

      this.loading = false;

      return;
    }


    // -----------------------------------------
    // Get manager ID
    // -----------------------------------------

    this.managerId = user.id;


    console.log(
      'Logged-in manager:',
      user
    );

    console.log(
      'Logged-in manager ID:',
      this.managerId
    );


    // -----------------------------------------
    // Load matrix
    // -----------------------------------------

    this.loadMatrix();

  }


  // =========================================
  // LOAD TEAM MATRIX
  // =========================================

  loadMatrix(): void {

    this.loading = true;

    this.error = '';


    this.managerService
      .getSkillMatrix(this.managerId)
      .subscribe({

        next: data => {

          console.log(
            'Team skill matrix:',
            data
          );


          this.matrix = data;


          // -------------------------------------
          // Extract competencies
          // -------------------------------------

          this.extractCompetencies();


          // -------------------------------------
          // Select first employee
          // -------------------------------------

          if (this.matrix.length > 0) {

            this.selectEmployee(
              this.matrix[0]
            );

          }


          this.loading = false;

        },


        error: err => {

          console.error(
            'Error loading skill matrix:',
            err
          );

          this.error =
            'Unable to load skill matrix.';

          this.loading = false;

        }

      });

  }


  // =========================================
  // LOAD EMPLOYEE MATRIX
  // =========================================

  loadEmployeeMatrix(
    employeeId: number
  ): void {

    console.log(
      'Loading employee skill matrix for:',
      employeeId
    );


    this.managerService
      .getEmployeeSkillMatrix(employeeId)
      .subscribe({

        next: data => {

          console.log(
            'Employee skill matrix:',
            data
          );

          this.employeeMatrix = data;

        },


        error: err => {

          console.error(
            'Error loading employee skill matrix:',
            err
          );

          this.employeeMatrix = [];

        }

      });

  }


  // =========================================
  // EXTRACT COMPETENCIES
  // =========================================

  extractCompetencies(): void {

    const set =
      new Set<string>();


    this.matrix.forEach(row => {

      if (!row.competences) {
        return;
      }


      Object.keys(
        row.competences
      ).forEach(skill => {

        set.add(skill);

      });

    });


    this.competencies =
      Array.from(set);

  }


  // =========================================
  // SELECT EMPLOYEE
  // =========================================

  selectEmployee(
    employee: SkillMatrix
  ): void {

    this.selectedEmployee =
      employee;


    // -----------------------------------------
    // Load detailed matrix
    // -----------------------------------------

    this.loadEmployeeMatrix(
      employee.utilisateurId
    );

  }


  // =========================================
  // GET GAP
  // =========================================

  getGap(
    skill: string
  ): number | null {

    if (!this.selectedEmployee) {
      return null;
    }


    const current =
      this.selectedEmployee
        .competences[skill];


    if (current === undefined) {
      return null;
    }


    // -----------------------------------------
    // Temporary required level
    //
    // TODO:
    // Replace this with the actual
    // PosteCompetence niveauRequis
    // -----------------------------------------

    const required = 0;


    return current - required;

  }


  // =========================================
  // EMPLOYEE SELECT CHANGE
  // =========================================

  onEmployeeChange(
    event: Event
  ): void {

    const select =
      event.target as HTMLSelectElement;


    const employeeId =
      Number(select.value);


    const employee =
      this.matrix.find(
        e =>
          e.utilisateurId === employeeId
      );


    if (employee) {

      this.selectEmployee(
        employee
      );

    }

  }

}