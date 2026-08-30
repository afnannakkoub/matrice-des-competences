import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPosteCompetenceComponent } from './edit-poste-competence.component';

describe('EditPosteCompetenceComponent', () => {
  let component: EditPosteCompetenceComponent;
  let fixture: ComponentFixture<EditPosteCompetenceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditPosteCompetenceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditPosteCompetenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
