import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowQuizComponent } from './show-quiz.component';

describe('ShowQuizComponent', () => {
  let component: ShowQuizComponent;
  let fixture: ComponentFixture<ShowQuizComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShowQuizComponent]
    });
    fixture = TestBed.createComponent(ShowQuizComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
