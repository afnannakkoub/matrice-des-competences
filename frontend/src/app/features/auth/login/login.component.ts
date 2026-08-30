import {
  Component,
  inject
} from '@angular/core';

import {
  CommonModule
} from '@angular/common';

import {
  FormsModule
} from '@angular/forms';

import {
  Router
} from '@angular/router';

import {
  AuthService
} from '../../../core/services/auth.service';


@Component({
  selector: 'app-login',

  standalone: true,

  imports: [
    CommonModule,
    FormsModule
  ],

  templateUrl: './login.component.html',

  styleUrl: './login.component.css'
})
export class LoginComponent {

  private authService =
    inject(AuthService);

  private router =
    inject(Router);


  // =====================================================
  // FORM
  // =====================================================

  email = '';

  motDePasse = '';


  // =====================================================
  // STATE
  // =====================================================

  loading = false;

  error = '';


  // =====================================================
  // LOGIN
  // =====================================================

  login(): void {

    this.error = '';

    this.loading = true;


    this.authService
      .login(
        this.email,
        this.motDePasse
      )
      .subscribe({

        next: user => {

          console.log(
            'Logged in user:',
            user
          );


          // ==========================================
          // REDIRECT ACCORDING TO ROLE
          // ==========================================

          if (user.role === 'ADMIN') {

            this.router.navigate([
              '/admin/dashboard'
            ]);

          }

          else if (user.role === 'MANAGER') {

            this.router.navigate([
              '/manager/dashboard'
            ]);

          }

          else if (
            user.role === 'EMPLOYE'
          ) {

            this.router.navigate([
              '/employee/dashboard'
            ]);

          }

          else {

            this.error =
              'Unknown user role.';

          }


          this.loading = false;

        },


        error: err => {

          console.error(
            'Login error:',
            err
          );


          this.error =
            typeof err.error === 'string'
              ? err.error
              : 'Email or password is incorrect.';


          this.loading = false;

        }

      });

  }

}