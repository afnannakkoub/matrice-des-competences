import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeSkillMatrixComponent } from './employee-skill-matrix.component';

describe('EmployeeSkillMatrixComponent', () => {
  let component: EmployeeSkillMatrixComponent;
  let fixture: ComponentFixture<EmployeeSkillMatrixComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeeSkillMatrixComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmployeeSkillMatrixComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
