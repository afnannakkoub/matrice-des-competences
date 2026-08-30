import {
  Component,
  OnInit,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { ManagerService } from '../../../core/services/manager.service';
import { Employee } from '../../../core/models/employee';
import { AuthService } from '../../../core/services/auth.service';


@Component({
  selector: 'app-my-team',

  standalone: true,

  imports: [
    CommonModule
  ],

  templateUrl: './my-team.component.html',

  styleUrl: './my-team.component.css'
})
export class MyTeamComponent implements OnInit {

  private managerService =
    inject(ManagerService);

  private router =
    inject(Router);

  private authService =
    inject(AuthService);


  // =========================================
  // DATA
  // =========================================

  employees: Employee[] = [];

  managerId!: number;


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

      this.router.navigate([
        '/login'
      ]);

      return;
    }


    // -----------------------------------------
    // Check role
    // -----------------------------------------

    if (user.role !== 'MANAGER') {

      console.error(
        'User is not a manager.'
      );

      this.error =
        'You are not authorized to access the team.';

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
    // Load team
    // -----------------------------------------

    this.loadTeam();

  }


  // =========================================
  // LOAD TEAM
  // =========================================

  loadTeam(): void {

    this.loading = true;

    this.error = '';


    this.managerService
      .getTeam(this.managerId)
      .subscribe({

        next: data => {

          console.log(
            'Manager team:',
            data
          );

          this.employees = data;

          this.loading = false;

        },


        error: err => {

          console.error(
            'Error loading team:',
            err
          );

          this.error =
            'Unable to load your team.';

          this.loading = false;

        }

      });

  }


  // =========================================
  // OPEN EMPLOYEE SKILL MATRIX
  // =========================================

  openMatrix(employeeId: number): void {

    this.router.navigate([
      '/manager/employee-skill-matrix',
      employeeId
    ]);

  }


  // =========================================
  // OPEN EMPLOYEE EVALUATION
  // =========================================

  evaluate(employeeId: number): void {

    this.router.navigate([
      '/manager/evaluate',
      employeeId
    ]);

  }

}