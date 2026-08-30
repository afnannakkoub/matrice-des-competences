import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { EmployeeService } from '../../../core/services/employee.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {

  private employeeService = inject(EmployeeService);
  private authService = inject(AuthService);
  private router = inject(Router);

  // ==========================================
  // LOGGED-IN EMPLOYEE
  // ==========================================

  employeeId!: number;

  employee: any = null;

  loading = true;

  saving = false;

  error = '';

  editMode = false;


  // ==========================================
  // INIT
  // ==========================================

  ngOnInit(): void {

    const userId = this.authService.getUserId();

    if (!userId) {

      console.error('No logged-in user found.');

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

    this.loadProfile();
  }


  // ==========================================
  // LOAD PROFILE
  // ==========================================

  loadProfile(): void {

    this.loading = true;

    this.employeeService
      .getProfile(this.employeeId)
      .subscribe({

        next: data => {

          console.log(
            'Employee profile:',
            data
          );

          this.employee = data;

          this.loading = false;

        },

        error: err => {

          console.error(err);

          this.error =
            'Unable to load your profile.';

          this.loading = false;

        }

      });
  }


  // ==========================================
  // ENABLE EDIT MODE
  // ==========================================

  editProfile(): void {

    this.editMode = true;

  }


  // ==========================================
  // CANCEL EDIT
  // ==========================================

  cancelEdit(): void {

    this.editMode = false;

    // Reload original data
    this.loadProfile();

  }


  // ==========================================
  // SAVE PROFILE
  // ==========================================

  saveProfile(): void {

    if (!this.employee) {

      return;

    }

    this.saving = true;

    this.employeeService
      .updateProfile(
        this.employeeId,
        this.employee
      )
      .subscribe({

        next: data => {

          console.log(
            'Profile updated:',
            data
          );

          this.employee = data;

          this.editMode = false;

          this.saving = false;

          alert(
            'Your profile has been updated successfully.'
          );

        },

        error: err => {

          console.error(err);

          this.saving = false;

          alert(
            'Error while updating your profile.'
          );

        }

      });

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