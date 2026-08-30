import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { CompetenceFormComponent } from '../../components/competence-form/competence-form.component';
import { CompetenceService } from '../../services/competence.service';
import { Competence } from '../../models/competence';

@Component({
  selector: 'app-add-competence',
  standalone: true,
  imports: [
    CommonModule,
    CompetenceFormComponent
  ],
  templateUrl: './add-competence.component.html',
  styleUrl: './add-competence.component.css'
})
export class AddCompetenceComponent {

  private competenceService = inject(CompetenceService);

  private router = inject(Router);

  save(competence: Competence) {

    this.competenceService.create(competence)

      .subscribe({

        next: () => {

          alert('Competence created successfully.');

          this.router.navigate([
            '/admin/competences'
          ]);

        },

        error: err => console.error(err)

      });

  }

}