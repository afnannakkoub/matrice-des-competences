import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '../../../../../environments/environment';

import { PosteCompetence } from '../models/poste-competence';

@Injectable({
  providedIn: 'root'
})
export class PosteCompetenceService {

  private http = inject(HttpClient);

  private api = environment.apiUrl + '/poste-competences';

  // ==========================
  // GET ALL
  // ==========================

  getAll(): Observable<PosteCompetence[]> {

    return this.http.get<PosteCompetence[]>(this.api);

  }

  // ==========================
  // GET BY ID
  // ==========================

  getById(id: number): Observable<PosteCompetence> {

    return this.http.get<PosteCompetence>(

      `${this.api}/${id}`

    );

  }

  // ==========================
  // GET BY POSITION
  // ==========================

  getByPoste(poste: string): Observable<PosteCompetence[]> {

    return this.http.get<PosteCompetence[]>(

      `${this.api}/poste/${poste}`

    );

  }

  // ==========================
  // CREATE
  // ==========================

  create(posteCompetence: PosteCompetence)
    : Observable<PosteCompetence> {

    return this.http.post<PosteCompetence>(

      this.api,

      posteCompetence

    );

  }

  // ==========================
  // UPDATE
  // ==========================

  update(
    id: number,
    posteCompetence: PosteCompetence
  ): Observable<PosteCompetence> {

    return this.http.put<PosteCompetence>(

      `${this.api}/${id}`,

      posteCompetence

    );

  }

  // ==========================
  // DELETE
  // ==========================

  delete(id: number): Observable<void> {

    return this.http.delete<void>(

      `${this.api}/${id}`

    );

  }

}