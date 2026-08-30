import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PosteCompetenceFormComponent } from './poste-competence-form.component';

describe('PosteCompetenceFormComponent', () => {
  let component: PosteCompetenceFormComponent;
  let fixture: ComponentFixture<PosteCompetenceFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PosteCompetenceFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PosteCompetenceFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
