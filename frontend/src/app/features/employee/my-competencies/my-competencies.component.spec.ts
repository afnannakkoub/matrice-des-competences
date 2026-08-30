import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyCompetenciesComponent } from './my-competencies.component';

describe('MyCompetenciesComponent', () => {
  let component: MyCompetenciesComponent;
  let fixture: ComponentFixture<MyCompetenciesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyCompetenciesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyCompetenciesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
