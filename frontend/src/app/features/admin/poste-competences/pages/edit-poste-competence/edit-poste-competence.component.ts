import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

import { PosteCompetenceFormComponent } from '../../components/poste-competence-form/poste-competence-form.component';

import { PosteCompetence } from '../../models/poste-competence';
import { PosteCompetenceService } from '../../services/poste-competence.service';

@Component({
  selector: 'app-edit-poste-competence',
  standalone: true,
  imports: [
    CommonModule,
    PosteCompetenceFormComponent
  ],
  templateUrl: './edit-poste-competence.component.html',
  styleUrl: './edit-poste-competence.component.css'
})
export class EditPosteCompetenceComponent implements OnInit {

  private route = inject(ActivatedRoute);

  private router = inject(Router);

  private service = inject(PosteCompetenceService);

  posteCompetence?: PosteCompetence;

  ngOnInit(): void {

    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.service.getById(id).subscribe({

      next: data => {

        this.posteCompetence = data;

      },

      error: err => console.error(err)

    });

  }

  update(posteCompetence: PosteCompetence): void {

    this.service.update(

      this.posteCompetence!.id!,

      posteCompetence

    ).subscribe({

      next: () => {

        alert('Requirement updated successfully.');

        this.router.navigate([

          '/admin/poste-competences'

        ]);

      },

      error: err => console.error(err)

    });

  }

}