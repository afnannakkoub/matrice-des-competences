import { Injectable , inject  } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { Dashboard } from '../models/dashboard.model';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})


export class DashboardService {

  private http = inject(HttpClient);

  private api = environment.apiUrl;

  getDashboard(): Observable<Dashboard> {

    return this.http.get<Dashboard>(
      `${this.api}/admin/dashboard`
    );

  }
}
