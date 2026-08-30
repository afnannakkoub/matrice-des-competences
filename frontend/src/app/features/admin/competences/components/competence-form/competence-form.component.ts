import {
  Component,
  EventEmitter,
  Input,
  Output,
  OnInit,
  OnChanges,
  SimpleChanges,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { Competence } from '../../models/competence';
import { Category } from '../../../categories/models/category';

import { CategoryService } from '../../../categories/services/category.service';

@Component({
  selector: 'app-competence-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './competence-form.component.html',
  styleUrl: './competence-form.component.css'
})
export class CompetenceFormComponent implements OnInit, OnChanges {

  private fb = inject(FormBuilder);

  private categoryService = inject(CategoryService);

  @Input()

  competence?: Competence;

  @Output()

  saveCompetence = new EventEmitter<Competence>();

  categories: Category[] = [];

  form!: FormGroup;

  ngOnInit(): void {

    this.form = this.fb.group({

      nom: ['', Validators.required],

      description: ['', Validators.required],

      archive: [false],

      categorie: [null, Validators.required]

    });

    this.loadCategories();

  }

  ngOnChanges(changes: SimpleChanges): void {

    if (changes['competence'] && this.competence && this.form) {

      this.form.patchValue({

        nom: this.competence.nom,

        description: this.competence.description,

        archive: this.competence.archive,

        categorie: this.competence.categorie

      });

    }

  }

  loadCategories() {

    this.categoryService.getAll().subscribe({

      next: data => {

        this.categories = data;

      },

      error: err => console.error(err)

    });

  }

  submit() {

    if (this.form.invalid) {

      this.form.markAllAsTouched();

      return;

    }

    this.saveCompetence.emit(this.form.value);

  }

}