import {
  Component,
  EventEmitter,
  Input,
  
  OnInit,
  Output,
  inject
} from '@angular/core';
import { UserService } from '../../../users/services/user.service';
import { CommonModule } from '@angular/common';

import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { Competence } from '../../../competences/models/competence';
import { CompetenceService } from '../../../competences/services/competence.service';

import { PosteCompetence } from '../../models/poste-competence';

@Component({
  selector: 'app-poste-competence-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './poste-competence-form.component.html',
  styleUrl: './poste-competence-form.component.css'
})
export class PosteCompetenceFormComponent implements OnInit {

  @Input()

  posteCompetence?: PosteCompetence;

  @Output()

  save = new EventEmitter<PosteCompetence>();

  private fb = inject(FormBuilder);

  private competenceService = inject(CompetenceService);
  private userService = inject(UserService);

  competences: Competence[] = [];
  postes: string[] = [];

  niveaux = [0, 1, 2, 3, 4];

  form!: FormGroup;

  ngOnInit(): void {

    this.form = this.fb.group({

      poste: ['', Validators.required],

      competence: [null, Validators.required],

      niveauRequis: [0, Validators.required]

    });

   this.loadCompetences();
   this.loadPostes();

  }



 loadCompetences(): void {

  this.competenceService.getAll().subscribe({

    next: data => {

      this.competences = data;

      if (this.posteCompetence) {

        this.form.patchValue({

          poste: this.posteCompetence.poste,

          competence: this.competences.find(

            c => c.id === this.posteCompetence!.competence.id

          ),

          niveauRequis: this.posteCompetence.niveauRequis

        });

      }

    },

    error: err => console.error(err)

  });

}
loadPostes(): void {

  this.userService.getPostes().subscribe({

    next: data => {

      this.postes = data;

    },

    error: err => console.error(err)

  });

}

  submit(): void {

    if (this.form.invalid) {

      this.form.markAllAsTouched();

      return;

    }

    this.save.emit(this.form.value);

  }

}