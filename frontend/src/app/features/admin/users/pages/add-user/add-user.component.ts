import { Component,inject } from '@angular/core';
import { UserFormComponent } from '../../components/user-form/user-form.component';
import { Router } from '@angular/router';

import { UserService } from '../../services/user.service';
@Component({
  selector: 'app-add-user',
  imports: [
    UserFormComponent],
  templateUrl: './add-user.component.html',
  styleUrl: './add-user.component.css'
})
export class AddUserComponent {
private userService = inject(UserService);

private router = inject(Router);

save(user: any) {

  this.userService.create(user).subscribe({

    next: () => {

      alert('User created successfully!');

      this.router.navigate(['/admin/users']);

    },

    error: err => {

      console.error(err);

      alert('Unable to create user.');

    }

  });

}
}
