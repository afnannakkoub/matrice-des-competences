import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  Router,
  RouterLink,
  RouterLinkActive,
  NavigationEnd
} from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {

  private router = inject(Router);

  currentArea: 'admin' | 'manager' | 'employee' = 'admin';

  ngOnInit(): void {

    // Determine initial area
    this.updateArea(this.router.url);

    // Update sidebar whenever the route changes
    this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd)
      )
      .subscribe((event: NavigationEnd) => {
        this.updateArea(event.urlAfterRedirects);
      });
  }

  private updateArea(url: string): void {

    if (url.startsWith('/manager')) {

      this.currentArea = 'manager';

    } else if (url.startsWith('/employee')) {

      this.currentArea = 'employee';

    } else {

      this.currentArea = 'admin';

    }
  }
}