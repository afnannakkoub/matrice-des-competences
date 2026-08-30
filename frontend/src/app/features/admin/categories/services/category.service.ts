import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../models/category';
import { environment } from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private http = inject(HttpClient);

  private api = environment.apiUrl + '/categories';

  getAll() {
    return this.http.get<Category[]>(this.api);
  }

  getById(id: number) {
    return this.http.get<Category>(`${this.api}/${id}`);
  }

  create(category: Category) {
    return this.http.post(this.api, category);
  }

  update(id: number, category: Category) {
    return this.http.put(`${this.api}/${id}`, category);
  }

  delete(id: number) {
    return this.http.delete(`${this.api}/${id}`);
  }

}