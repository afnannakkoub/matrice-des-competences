import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

import { CategoryFormComponent } from '../../components/category-form/category-form.component';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../models/category';

@Component({
  selector: 'app-edit-category',
  standalone: true,
  imports: [
    CommonModule,
    CategoryFormComponent
  ],
  templateUrl: './edit-category.component.html',
  styleUrl: './edit-category.component.css'
})
export class EditCategoryComponent implements OnInit {

  private categoryService = inject(CategoryService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  category!: Category;

  categoryId!: number;

  ngOnInit(): void {

    this.categoryId = Number(

      this.route.snapshot.paramMap.get('id')

    );

    this.loadCategory();

  }

  loadCategory() {

    this.categoryService.getById(this.categoryId)

      .subscribe({

        next: data => {

          this.category = data;

        },

        error: err => console.error(err)

      });

  }

  update(category: Category) {

    this.categoryService.update(

      this.categoryId,

      category

    ).subscribe({

      next: () => {

        alert('Category updated successfully.');

        this.router.navigate(['/admin/categories']);

      },

      error: err => console.error(err)

    });

  }

}