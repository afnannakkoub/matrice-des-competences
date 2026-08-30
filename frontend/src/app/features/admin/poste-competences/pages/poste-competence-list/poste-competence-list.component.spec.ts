import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PosteCompetenceListComponent } from './poste-competence-list.component';

describe('PosteCompetenceListComponent', () => {
  let component: PosteCompetenceListComponent;
  let fixture: ComponentFixture<PosteCompetenceListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PosteCompetenceListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PosteCompetenceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
