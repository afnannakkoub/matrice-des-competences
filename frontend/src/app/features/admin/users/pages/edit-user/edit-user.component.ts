
import { Component, OnInit, ViewChild, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

import { UserFormComponent } from '../../components/user-form/user-form.component';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';
@Component({
  selector: 'app-edit-user',
  imports: [UserFormComponent],
  templateUrl: './edit-user.component.html',
  styleUrl: './edit-user.component.css'
})
export class EditUserComponent implements OnInit  {
  private route = inject(ActivatedRoute);

private router = inject(Router);

private userService = inject(UserService);
@ViewChild(UserFormComponent)

userForm!: UserFormComponent;
userId!: number;

user!: User;

 ngOnInit(): void {

        this.userId = Number(

            this.route.snapshot.paramMap.get('id')

        );

        this.loadUser();

    }

    loadUser() {

    this.userService.getById(this.userId)

        .subscribe({

            next: (data) => {

                this.user = data;

            },

            error: err => console.error(err)

        });

}

update(user: User) {

  this.userService.update(this.userId, user)

      .subscribe({

          next: () => {

              alert('User updated successfully');

              this.router.navigate(['/admin/users']);

          },

          error: err => console.error(err)

      });

}
}
