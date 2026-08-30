import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { LoginComponent } from './features/auth/login/login.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';

import { DashboardComponent } from './features/admin/dashboard/dashboard.component';
import { ReportsComponent } from './features/admin/reports/reports.component';
import { PosteCompetenceListComponent } from './features/admin/poste-competences/pages/poste-competence-list/poste-competence-list.component';
import { AddPosteCompetenceComponent } from './features/admin/poste-competences/pages/add-poste-competence/add-poste-competence.component';
import { EditPosteCompetenceComponent } from './features/admin/poste-competences/pages/edit-poste-competence/edit-poste-competence.component';

import { UserListComponent } from './features/admin/users/pages/user-list/user-list.component';
import { AddUserComponent } from './features/admin/users/pages/add-user/add-user.component';
import { EditUserComponent } from './features/admin/users/pages/edit-user/edit-user.component';

import { CategoryListComponent } from './features/admin/categories/pages/category-list/category-list.component';
import { AddCategoryComponent } from './features/admin/categories/pages/add-category/add-category.component';
import { EditCategoryComponent } from './features/admin/categories/pages/edit-category/edit-category.component';

import { CompetenceListComponent } from './features/admin/competences/pages/competence-list/competence-list.component';
import { AddCompetenceComponent } from './features/admin/competences/pages/add-competence/add-competence.component';
import { EditCompetenceComponent } from './features/admin/competences/pages/edit-competence/edit-competence.component';

import { EmployeeSkillMatrixComponent } from './features/manager/employee-skill-matrix/employee-skill-matrix.component';

import { DashboardComponent as ManagerDashboardComponent } from './features/manager/dashboard/dashboard.component';
import { MyTeamComponent } from './features/manager/my-team/my-team.component';
import { SkillMatrixComponent } from './features/manager/skill-matrix/skill-matrix.component';
import { ValidationsComponent } from './features/manager/validations/validations.component';
import { StatisticsComponent } from './features/manager/statistics/statistics.component';
import { EvaluateComponent } from './features/manager/evaluate/evaluate.component';

import { EmployeeDashboardComponent } from './features/employee/dashboard/dashboard.component';
import { MyCompetenciesComponent } from './features/employee/my-competencies/my-competencies.component';
import { SkillMatrixComponent as EmployeeSkillMatrixComponente } from './features/employee/skill-matrix/skill-matrix.component';
import { ProfileComponent } from './features/employee/profile/profile.component';
export const routes: Routes = [


  // ==========================================
  // LOGIN
  // ==========================================

  {
    path: 'login',
    component: LoginComponent
  },


  // ==========================================
  // APPLICATION
  // ==========================================

  {
    path: '',
    component: MainLayoutComponent,

    canActivate: [
      authGuard
    ],

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
        component: DashboardComponent,
        canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
      },

      {
        path: 'admin/users',
        component: UserListComponent,
        canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
      },

      {
        path: 'admin/users/add',
        component: AddUserComponent,
        canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
      },

      {
        path: 'admin/users/edit/:id',
        component: EditUserComponent,
        canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
      },

      
      {
             path: 'admin/categories',
               component: CategoryListComponent,
               canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
      },
{
  path: 'admin/categories/add',
  component: AddCategoryComponent,
  canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
},
{
  path: 'admin/categories/edit/:id',
  component: EditCategoryComponent,
  canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
},

      {
  path: 'admin/competences',
  component: CompetenceListComponent,
  canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
},
{
  path: 'admin/competences/add',
  component: AddCompetenceComponent,
  canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
},
{
  path: 'admin/competences/edit/:id',
  component: EditCompetenceComponent,
  canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
},

      {
        path: 'admin/reports',
        component: ReportsComponent,
        canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
      },
{
    path: 'admin/poste-competences',
    component: PosteCompetenceListComponent,
    canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
},
{
    path: 'admin/poste-competences/add',
    component: AddPosteCompetenceComponent,
    canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
},
{
    path: 'admin/poste-competences/edit/:id',
    component: EditPosteCompetenceComponent,
    canActivate: [authGuard],
  data: {
    roles: ['ADMIN']}
},
       // ======================
      // MANAGER 
      // ======================
{
  path: 'manager/dashboard', 
  component: ManagerDashboardComponent,
  canActivate: [authGuard],
  data: {
    roles: ['MANAGER']}

},
{
  path: 'manager/team',
  component: MyTeamComponent,
  canActivate: [authGuard],
  data: {
    roles: ['MANAGER']}
},
{
  path: 'manager/matrix',
  component: SkillMatrixComponent,
  canActivate: [authGuard],
  data: {
    roles: ['MANAGER']}
},
{
  path: 'manager/validations',
  component: ValidationsComponent,
  canActivate: [authGuard],
  data: {
    roles: ['MANAGER']}
},
{
  path: 'manager/statistics',
  component: StatisticsComponent,
  canActivate: [authGuard],
  data: {
    roles: ['MANAGER']}
},
{
    path: 'manager/employee-skill-matrix/:id',
    component: EmployeeSkillMatrixComponent,
    canActivate: [authGuard],
  data: {
    roles: ['MANAGER']}
},
{
    path: 'manager/evaluate/:id',
    component: EvaluateComponent,
    canActivate: [authGuard],
  data: {
    roles: ['MANAGER']}
},
      // ======================
      // EMPLOYEE (Later)
      // ======================

       {
         path: 'employee/dashboard',
        component: EmployeeDashboardComponent,
         canActivate: [authGuard],
  data: {
    roles: ['EMPLOYE']
  }
       },
       {
        path: 'employee/competencies',
        component: MyCompetenciesComponent,
         canActivate: [authGuard],
  data: {
    roles: ['EMPLOYE']
  }
       },

       {
          path: 'employee/skill-matrix',
           component: EmployeeSkillMatrixComponente,
            canActivate: [authGuard],
  data: {
    roles: ['EMPLOYE']
  }
        },

       

       
       

     {
      path: 'employee/profile',
      component: ProfileComponent,
       canActivate: [authGuard],
  data: {
    roles: ['EMPLOYE']
  }
     },

      

    ]
  },


  // ==========================================
  // DEFAULT
  // ==========================================

  {
    path: '**',
    redirectTo: 'login'
  },


  {
    path: '',
    component: MainLayoutComponent,

    children: []
    

  },

  {
    path: '**',
    redirectTo: ''
  }

];