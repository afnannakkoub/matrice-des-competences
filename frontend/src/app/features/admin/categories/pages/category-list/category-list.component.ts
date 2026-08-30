import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { Category } from '../../models/category';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-category-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './category-list.component.html',
  styleUrl: './category-list.component.css'
})
export class CategoryListComponent implements OnInit {

  private categoryService = inject(CategoryService);

  categories: Category[] = [];

  filteredCategories: Category[] = [];

  search = '';

  loading = true;

  ngOnInit(): void {

    this.loadCategories();

  }

  loadCategories() {

    this.categoryService.getAll().subscribe({

      next: data => {

        this.categories = data;

        this.filteredCategories = [...data];

        this.loading = false;

      },

      error: err => {

        console.error(err);

        this.loading = false;

      }

    });

  }

  filterCategories() {

    this.filteredCategories = this.categories.filter(category =>

      category.nom.toLowerCase().includes(

        this.search.toLowerCase()

      )

    );

  }

  deleteCategory(id: number) {

    const confirmed = confirm(

      'Are you sure you want to delete this category?'

    );

    if (!confirmed) {

      return;

    }

    this.categoryService.delete(id).subscribe({

      next: () => {

        alert('Category deleted successfully.');

        this.loadCategories();

      },

      error: err => {

        console.error(err);

      }

    });

  }

}