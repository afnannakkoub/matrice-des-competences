import {
  Component,
  OnInit,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';

import {
  ManagerService,
  StatisticsDTO
} from '../../../core/services/manager.service';


@Component({
  selector: 'app-statistics',

  standalone: true,

  imports: [
    CommonModule
  ],

  templateUrl: './statistics.component.html',

  styleUrl: './statistics.component.css'
})
export class StatisticsComponent implements OnInit {

  private managerService =
    inject(ManagerService);


  // =====================================================
  // MANAGER
  // =====================================================

private authService = inject(AuthService);


  // =====================================================
  // DATA
  // =====================================================

  statistics: StatisticsDTO | null = null;


  // =====================================================
  // STATE
  // =====================================================

  loading = true;

  error = '';


  // =====================================================
  // INIT
  // =====================================================

  ngOnInit(): void {

    this.loadStatistics();

  }


  // =====================================================
  // LOAD STATISTICS
  // =====================================================

  loadStatistics(): void {

  this.loading = true;
  this.error = '';

  const managerId =
    this.authService.getUserId();

  if (managerId === null) {

    this.error = 'User is not authenticated.';
    this.loading = false;

    return;
  }

  this.managerService
    .getStatistics(managerId)
    .subscribe({

      next: data => {

        console.log(
          'Statistics:',
          data
        );

        this.statistics = data;

        this.loading = false;

      },

      error: err => {

        console.error(
          'Statistics error:',
          err
        );

        this.error =
          'Unable to load statistics.';

        this.loading = false;

      }

    });

}

  // =====================================================
  // EMPLOYEE LEVEL
  // =====================================================

 getEmployeeLevelCount(level: number): number {

  if (!this.statistics) {
    return 0;
  }

  switch (level) {

    case 0:
      return this.statistics.level0;

    case 1:
      return this.statistics.level1;

    case 2:
      return this.statistics.level2;

    case 3:
      return this.statistics.level3;

    case 4:
      return this.statistics.level4;

    default:
      return 0;
  }
}


  // =====================================================
  // STATUS COUNT
  // =====================================================

  getStatusCount(
    status: string
  ): number {

    if (!this.statistics) {
      return 0;
    }


    // -----------------------------------------
    // PENDING
    // -----------------------------------------

    if (status === 'EN_ATTENTE') {

      return this.statistics.pendingCompetencies ?? 0;

    }


    // -----------------------------------------
    // VALIDATED
    // -----------------------------------------

    if (status === 'VALIDE') {

      return this.statistics.validatedCompetencies ?? 0;

    }


    return 0;

  }


  // =====================================================
  // FORMAT AVERAGE
  // =====================================================

  formatNumber(
    value: number | null | undefined
  ): string {

    if (
      value === null ||
      value === undefined
    ) {

      return '0';

    }

    return value.toFixed(1);

  }

}