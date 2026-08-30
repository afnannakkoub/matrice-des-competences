import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { Competence } from '../../models/competence';
import { CompetenceService } from '../../services/competence.service';
import { Category } from '../../../categories/models/category';

@Component({
  selector: 'app-competence-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './competence-list.component.html',
  styleUrl: './competence-list.component.css'
})
export class CompetenceListComponent implements OnInit {

  private competenceService = inject(CompetenceService);

  competences: Competence[] = [];

  filteredCompetences: Competence[] = [];

  categories: Category[] = [];

  search = '';

  selectedCategory = 'ALL';

  loading = true;

  ngOnInit(): void {

    this.loadCompetences();

  }

  loadCompetences() {

    this.competenceService.getAll().subscribe({

      next: data => {

        this.competences = data;

        this.filteredCompetences = [...data];

        this.categories = [
          ...new Map(
            data.map(c => [c.categorie.id, c.categorie])
          ).values()
        ];

        this.loading = false;

      },

      error: err => {

        console.error(err);

        this.loading = false;

      }

    });

  }

  filterCompetences() {

    this.filteredCompetences = this.competences.filter(c => {

      const matchesSearch =

        c.nom.toLowerCase().includes(this.search.toLowerCase())

        ||

        c.description.toLowerCase().includes(this.search.toLowerCase());

      const matchesCategory =

        this.selectedCategory === 'ALL'

        ||

        c.categorie.nom === this.selectedCategory;

      return matchesSearch && matchesCategory;

    });

  }

  deleteCompetence(id: number) {

    const confirmed = confirm(
      'Delete this competence?'
    );

    if (!confirmed) return;

    this.competenceService.delete(id).subscribe({

      next: () => {

        this.loadCompetences();

      },

      error: err => console.error(err)

    });

  }

}