import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CovoitOrganises } from './covoit-organises';

describe('CovoitOrganises', () => {
  let component: CovoitOrganises;
  let fixture: ComponentFixture<CovoitOrganises>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CovoitOrganises]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CovoitOrganises);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
