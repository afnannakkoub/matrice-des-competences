import { Injectable, inject } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { User } from '../models/user';

import { environment } from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private http = inject(HttpClient);

  private api = environment.apiUrl + '/utilisateurs';

 getAll(): Observable<User[]> {

  console.log("Calling API:", this.api);

  return this.http.get<User[]>(this.api);

}

  getById(id: number): Observable<User> {

    return this.http.get<User>(`${this.api}/${id}`);

  }

  create(user: User) {

    return this.http.post(this.api, user);

  }

  update(id: number, user: User) {

    return this.http.put(`${this.api}/${id}`, user);

  }

  delete(id: number) {

    return this.http.delete(`${this.api}/${id}`);

  }

}