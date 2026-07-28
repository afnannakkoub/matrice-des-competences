import { Routes } from '@angular/router';

import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';

import { DashboardComponent } from './features/admin/dashboard/dashboard.component';
import { CategoriesComponent } from './features/admin/categories/categories.component';
import { CompetencesComponent } from './features/admin/competences/competences.component';
import { ReportsComponent } from './features/admin/reports/reports.component';

import { UserListComponent } from './features/admin/users/pages/user-list/user-list.component';
import { AddUserComponent } from './features/admin/users/pages/add-user/add-user.component';
import { EditUserComponent } from './features/admin/users/pages/edit-user/edit-user.component';

export const routes: Routes = [

  {
    path: '',
    component: MainLayoutComponent,

    children: [
    // ======================
    // ADMIN
    // ======================

      {
        path: '',
        redirectTo: 'admin/dashboard',
        pathMatch: 'full'
      },

      {
        path: 'admin/dashboard',
        component: DashboardComponent
      },

      {
        path: 'admin/users',
        component: UserListComponent
      },

      {
        path: 'admin/users/add',
        component: AddUserComponent
      },

      {
        path: 'admin/users/edit/:id',
        component: EditUserComponent
      },

      {
        path: 'admin/categories',
        component: CategoriesComponent
      },

      {
        path: 'admin/competences',
        component: CompetencesComponent
      },

      {
        path: 'admin/reports',
        component: ReportsComponent
      }

       // ======================
      // MANAGER (Later)
      // ======================

      // {
      //   path: 'manager/dashboard',
      //   component: ManagerDashboardComponent
      // },

      // {
      //   path: 'manager/team',
      //   component: TeamComponent
      // },

      // {
      //   path: 'manager/matrix',
      //   component: TeamMatrixComponent
      // },

      // ======================
      // EMPLOYEE (Later)
      // ======================

      // {
      //   path: 'employee/dashboard',
      //   component: EmployeeDashboardComponent
      // },

      // {
      //   path: 'employee/profile',
      //   component: ProfileComponent
      // },

      // {
      //   path: 'employee/skills',
      //   component: MySkillsComponent
      // }

    ]

  },

  {
    path: '**',
    redirectTo: ''
  }

];