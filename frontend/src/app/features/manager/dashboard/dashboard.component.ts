import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { ManagerService } from '../../../core/services/manager.service';
import { ManagerDashboard } from '../../../core/models/manager-dashboard';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-manager-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

    private managerService = inject(ManagerService);
  private authService = inject(AuthService);

  router = inject(Router);

  dashboard?: ManagerDashboard;

  loading = true;

  error = '';

  ngOnInit(): void {

    const user = this.authService.getCurrentUser();

    // ==========================================
    // NO AUTHENTICATED USER
    // ==========================================

    if (!user) {

      this.router.navigate(['/login']);

      return;
    }

    // ==========================================
    // CHECK ROLE
    // ==========================================

    if (user.role !== 'MANAGER') {

      this.error = 'Access denied.';

      this.loading = false;

      return;
    }

    // ==========================================
    // GET REAL MANAGER ID
    // ==========================================

    const managerId = user.id;

    console.log('Logged-in manager:', user);
    console.log('Manager ID:', managerId);

    // ==========================================
    // LOAD DASHBOARD
    // ==========================================

    this.managerService
      .getDashboard(managerId)
      .subscribe({

        next: data => {

          this.dashboard = data;

          this.loading = false;

        },

        error: err => {

          console.error(
            'Manager dashboard error:',
            err
          );

          this.error =
            'Unable to load manager dashboard.';

          this.loading = false;

        }

      });

  }

}