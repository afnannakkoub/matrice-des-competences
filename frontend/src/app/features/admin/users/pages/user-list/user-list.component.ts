import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserService } from '../../services/user.service';
import { User } from '../../models/user';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [
  CommonModule,
  FormsModule ,
    RouterLink
  ],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit {
  selectedDepartment = '';
  departments: string[] = [];


  private userService = inject(UserService);

  users: User[] = [];
 filteredUsers: User[] = [];

  search = '';

 selectedRole = 'ALL';

  loading = true;

  ngOnInit(): void {

    this.loadUsers();

  }

  loadUsers() {

    this.userService.getAll().subscribe({

      next: (data) => {

        this.users = data;
        this.departments = [
        ...new Set(
           data.map(user => user.departement)
           )
          ];

       this.filteredUsers = data;

        this.loading = false;

      },

      error: (err) => {

        console.error(err);

        this.loading = false;

      }

    });

  }
  deleteUser(id: number): void {

  const confirmed = confirm(
    'Are you sure you want to delete this user?'
  );

  if (!confirmed) {
    return;
  }

  this.userService.delete(id).subscribe({

    next: () => {

      alert('User deleted successfully.');

      this.loadUsers();

    },

    error: err => {

      console.error(err);

      alert('Unable to delete the user.');

    }

  });

}
filterUsers() {

  this.filteredUsers = this.users.filter(user => {

    const fullName =
      (user.nom + ' ' + user.prenom).toLowerCase();

    const matchesSearch =

      fullName.includes(this.search.toLowerCase())

      ||

      user.email.toLowerCase().includes(this.search.toLowerCase());

    const matchesRole =

      this.selectedRole === 'ALL'

      ||

      user.role.nom === this.selectedRole;

    const matchesDepartment =

      this.selectedDepartment === 'ALL'

      ||

      user.departement === this.selectedDepartment;

    return matchesSearch
        && matchesRole
        && matchesDepartment;

  });

}

}