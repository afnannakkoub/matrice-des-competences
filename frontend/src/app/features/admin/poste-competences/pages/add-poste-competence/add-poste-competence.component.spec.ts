import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPosteCompetenceComponent } from './add-poste-competence.component';

describe('AddPosteCompetenceComponent', () => {
  let component: AddPosteCompetenceComponent;
  let fixture: ComponentFixture<AddPosteCompetenceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddPosteCompetenceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddPosteCompetenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
