import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

import { Competence } from '../../models/competence';
import { CompetenceService } from '../../services/competence.service';
import { CompetenceFormComponent } from '../../components/competence-form/competence-form.component';

@Component({
  selector: 'app-edit-competence',
  standalone: true,
  imports: [
    CommonModule,
    CompetenceFormComponent
  ],
  templateUrl: './edit-competence.component.html',
  styleUrl: './edit-competence.component.css'
})
export class EditCompetenceComponent implements OnInit {

  private competenceService = inject(CompetenceService);

  private route = inject(ActivatedRoute);

  private router = inject(Router);

  competence!: Competence;

  competenceId!: number;

  ngOnInit(): void {

    this.competenceId = Number(
      this.route.snapshot.paramMap.get('id')
    );

    this.loadCompetence();

  }

  loadCompetence() {

    this.competenceService
      .getById(this.competenceId)
      .subscribe({

        next: data => {

          this.competence = data;

        },

        error: err => console.error(err)

      });

  }

  update(competence: Competence) {

    this.competenceService
      .update(this.competenceId, competence)
      .subscribe({

        next: () => {

          alert('Competence updated successfully.');

          this.router.navigate([
            '/admin/competences'
          ]);

        },

        error: err => console.error(err)

      });

  }

}