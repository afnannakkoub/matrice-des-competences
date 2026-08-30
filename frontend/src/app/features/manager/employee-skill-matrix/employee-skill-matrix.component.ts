import {
  Component,
  OnInit,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { ManagerService } from '../../../core/services/manager.service';
import { EmployeeSkillMatrix } from '../../../core/models/employee-skill-matrix';

@Component({
  selector: 'app-employee-skill-matrix',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './employee-skill-matrix.component.html',
  styleUrl: './employee-skill-matrix.component.css'
})
export class EmployeeSkillMatrixComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private managerService = inject(ManagerService);

  employeeId!: number;

  matrix: EmployeeSkillMatrix[] = [];

  // ==========================================
  // ADD SKILL
  // ==========================================

  competences: any[] = [];

  showAddSkill = false;

  selectedCompetenceId: number | null = null;

  selectedLevel: number | null = null;

  saving = false;

  error = '';

  success = '';


  ngOnInit(): void {

    this.employeeId =
      Number(this.route.snapshot.paramMap.get('id'));

    console.log('Employee ID:', this.employeeId);

    this.loadMatrix();

    this.loadCompetences();
  }


  // ==========================================
  // LOAD MATRIX
  // ==========================================

  loadMatrix(): void {

    this.managerService
      .getEmployeeSkillMatrix(this.employeeId)
      .subscribe({

        next: data => {

          console.log('API DATA:', data);

          this.matrix = data;

        },

        error: err => {

          console.error(
            'Error loading employee matrix:',
            err
          );

        }

      });
  }


  // ==========================================
  // LOAD ALL COMPETENCES
  // ==========================================

  loadCompetences(): void {

    this.managerService
      .getCompetences()
      .subscribe({

        next: data => {

          this.competences = data;

          console.log(
            'Available competences:',
            this.competences
          );

        },

        error: err => {

          console.error(
            'Error loading competences:',
            err
          );

        }

      });

  }


  // ==========================================
  // OPEN FORM
  // ==========================================

  openAddSkill(): void {

    this.showAddSkill = true;

    this.selectedCompetenceId = null;

    this.selectedLevel = null;

    this.error = '';

    this.success = '';
  }


  // ==========================================
  // CLOSE FORM
  // ==========================================

  cancelAddSkill(): void {

    this.showAddSkill = false;

    this.selectedCompetenceId = null;

    this.selectedLevel = null;

    this.error = '';
  }


  // ==========================================
  // ADD SKILL
  // ==========================================

  addSkill(): void {

    this.error = '';
    this.success = '';

    if (!this.selectedCompetenceId) {

      this.error = 'Please select a competence.';

      return;
    }

    if (
      this.selectedLevel === null ||
      this.selectedLevel === undefined
    ) {

      this.error = 'Please select a level.';

      return;
    }

    this.saving = true;


    const data = {

      utilisateur: {
        id: this.employeeId
      },

      competence: {
        id: this.selectedCompetenceId
      },

      niveauEmploye: this.selectedLevel

    };


    console.log(
      'Creating employee competence:',
      data
    );


    this.managerService
      .addEmployeeCompetence(data)
      .subscribe({

        next: response => {

          console.log(
            'Competence created:',
            response
          );

          this.saving = false;

          this.success =
            'Skill associated successfully.';

          this.showAddSkill = false;

          this.selectedCompetenceId = null;

          this.selectedLevel = null;

          // Refresh matrix
          this.loadMatrix();

        },

        error: err => {

          console.error(
            'Error adding competence:',
            err
          );

          this.saving = false;

          this.error =
            'Unable to associate this skill.';

        }

      });

  }

}