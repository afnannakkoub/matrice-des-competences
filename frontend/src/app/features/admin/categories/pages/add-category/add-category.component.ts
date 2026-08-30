import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { CategoryFormComponent } from '../../components/category-form/category-form.component';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../models/category';

@Component({
  selector: 'app-add-category',
  standalone: true,
  imports: [
    CommonModule,
    CategoryFormComponent
  ],
  templateUrl: './add-category.component.html',
  styleUrl: './add-category.component.css'
})
export class AddCategoryComponent {

  private categoryService = inject(CategoryService);
  private router = inject(Router);

  save(category: Category) {

    this.categoryService.create(category).subscribe({

      next: () => {

        alert('Category created successfully.');

        this.router.navigate(['/admin/categories']);

      },

      error: err => console.error(err)

    });

  }

}