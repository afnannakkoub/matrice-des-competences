import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '../../../../../environments/environment';

import { Competence } from '../models/competence';

@Injectable({
  providedIn: 'root'
})
export class CompetenceService {

  private http = inject(HttpClient);

  private api = environment.apiUrl + '/competences';

  getAll(): Observable<Competence[]> {

    return this.http.get<Competence[]>(this.api);

  }

  getById(id: number): Observable<Competence> {

    return this.http.get<Competence>(`${this.api}/${id}`);

  }

  create(competence: Competence) {

    return this.http.post(this.api, competence);

  }

  update(id: number, competence: Competence) {

    return this.http.put(`${this.api}/${id}`, competence);

  }

  delete(id: number) {

    return this.http.delete(`${this.api}/${id}`);

  }

}