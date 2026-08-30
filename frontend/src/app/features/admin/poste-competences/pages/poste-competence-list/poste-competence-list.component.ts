import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

import { PosteCompetence } from '../../models/poste-competence';
import { PosteCompetenceService } from '../../services/poste-competence.service';

@Component({
  selector: 'app-poste-competence-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule
  ],
  templateUrl: './poste-competence-list.component.html',
  styleUrl: './poste-competence-list.component.css'
})
export class PosteCompetenceListComponent implements OnInit {

  private service = inject(PosteCompetenceService);

  private router = inject(Router);

  posteCompetences: PosteCompetence[] = [];

  ngOnInit(): void {

    this.loadPosteCompetences();

  }

  loadPosteCompetences(): void {

    this.service.getAll().subscribe({

      next: data => {

        this.posteCompetences = data;

      },

      error: err => console.error(err)

    });

  }

  add(): void {

    this.router.navigate([
      '/admin/poste-competences/add'
    ]);

  }

  edit(id: number): void {

    this.router.navigate([
      '/admin/poste-competences/edit',
      id
    ]);

  }

  delete(id: number): void {

    if (!confirm('Delete this required competence?')) {

      return;

    }

    this.service.delete(id).subscribe({

      next: () => this.loadPosteCompetences(),

      error: err => console.error(err)

    });

  }

}