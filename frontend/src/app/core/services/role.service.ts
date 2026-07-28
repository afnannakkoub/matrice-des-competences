import { Injectable, inject } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';

import { Role } from '../models/role';

@Injectable({
  providedIn: 'root'
})

export class RoleService {

  private http = inject(HttpClient);

  private api = environment.apiUrl + '/roles';

  getAll(): Observable<Role[]> {

    return this.http.get<Role[]>(this.api);

  }

}