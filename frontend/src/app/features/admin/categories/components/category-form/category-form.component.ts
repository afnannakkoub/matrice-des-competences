import { Component, EventEmitter, Input, Output, OnChanges, SimpleChanges, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { Category } from '../../models/category';

@Component({
  selector: 'app-category-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './category-form.component.html',
  styleUrl: './category-form.component.css'
})
export class CategoryFormComponent implements OnChanges {

  private fb = inject(FormBuilder);

  @Input()

  category?: Category;

  @Output()

  saveCategory = new EventEmitter<Category>();

  form: FormGroup = this.fb.group({

    nom: ['', Validators.required],

    description: ['', Validators.required]

  });

  ngOnChanges(changes: SimpleChanges): void {

    if (changes['category'] && this.category) {

      this.form.patchValue({

        nom: this.category.nom,

        description: this.category.description

      });

    }

  }

  submit() {

    if (this.form.invalid) {

      this.form.markAllAsTouched();

      return;

    }

    this.saveCategory.emit(this.form.value);

  }

}