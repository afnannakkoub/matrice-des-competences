import { Component, OnInit, inject, Output, EventEmitter,Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { OnChanges, SimpleChanges } from '@angular/core';
import { Role } from '../../../../../core/models/role';
import { RoleService } from '../../../../../core/services/role.service';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.css'
})
export class UserFormComponent implements OnInit, OnChanges {

  @Output()
  saveUser = new EventEmitter<any>();

  private userService = inject(UserService);
  private fb = inject(FormBuilder);
  private roleService = inject(RoleService);
@Input()

user?: User;

  roles: Role[] = [];
  users: User[] = [];
  managers: User[] = [];

  form!: FormGroup;

  ngOnInit(): void {

    this.form = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      motDePasse: ['', Validators.required],
      poste: ['', Validators.required],
      departement: ['', Validators.required],
      actif: [true],
      role: [null, Validators.required],
      manager: [null]
    });

    
    

    this.loadRoles();
    this.loadManagers();
    this.listenRoleChanges();
  }
  ngOnChanges(changes: SimpleChanges): void {

  if (changes['user'] && this.user && this.form) {

    this.form.patchValue({

      nom: this.user.nom,

      prenom: this.user.prenom,

      email: this.user.email,


      poste: this.user.poste,

      departement: this.user.departement,

      actif: this.user.actif,

      role: this.user.role,

      manager: this.user.manager

    });

  }

}
  listenRoleChanges() {

  this.form.get('role')?.valueChanges.subscribe(role => {

    const managerControl = this.form.get('manager');

    if (role?.nom === 'EMPLOYE') {

      managerControl?.setValidators([
        Validators.required
      ]);

    } else {

      managerControl?.clearValidators();

      managerControl?.setValue(null);

    }

    managerControl?.updateValueAndValidity();

  });

}

  loadRoles(): void {
    this.roleService.getAll().subscribe({
      next: data => {
        this.roles = data;
      },
      error: err => console.error(err)
    });
  }

  loadManagers(): void {
    this.userService.getAll().subscribe({
      next: users => {
        this.users = users;

        this.managers = users.filter(
          user => user.role.nom === 'MANAGER'
        );
      },
      error: err => console.error(err)
    });
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saveUser.emit(this.form.value);
  }
  get isEmployee(): boolean {

  const role = this.form.get('role')?.value;

  return role?.nom === 'EMPLOYE';

}


}