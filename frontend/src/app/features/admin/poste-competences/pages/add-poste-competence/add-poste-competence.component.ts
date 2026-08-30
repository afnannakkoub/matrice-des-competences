import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { PosteCompetenceFormComponent } from '../../components/poste-competence-form/poste-competence-form.component';

import { PosteCompetence } from '../../models/poste-competence';
import { PosteCompetenceService } from '../../services/poste-competence.service';

@Component({
  selector: 'app-add-poste-competence',
  standalone: true,
  imports: [
    CommonModule,
    PosteCompetenceFormComponent
  ],
  templateUrl: './add-poste-competence.component.html',
  styleUrl: './add-poste-competence.component.css'
})
export class AddPosteCompetenceComponent {

  private service = inject(PosteCompetenceService);

  private router = inject(Router);

  save(posteCompetence: PosteCompetence): void {

    this.service.create(posteCompetence).subscribe({

      next: () => {

        alert('Requirement added successfully.');

        this.router.navigate([
          '/admin/poste-competences'
        ]);

      },

      error: err => console.error(err)

    });

  }

}