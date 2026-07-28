import { Component, OnInit, inject } from '@angular/core';

import { CommonModule } from '@angular/common';

import { DashboardService } from '../../../core/services/dashboard.service';
import { Dashboard } from '../../../core/models/dashboard.model';

import { PageHeaderComponent } from '../../../shared/components/page-header/page-header.component';
import { StatCardComponent } from '../../../shared/components/stat-card/stat-card.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    PageHeaderComponent,
    StatCardComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

  private dashboardService = inject(DashboardService);

  dashboard!: Dashboard;

  ngOnInit(): void {

    this.dashboardService.getDashboard().subscribe({

      next: (data) => {

        this.dashboard = data;

      },

      error: (err) => {

        console.error(err);

      }

    });

  }

}