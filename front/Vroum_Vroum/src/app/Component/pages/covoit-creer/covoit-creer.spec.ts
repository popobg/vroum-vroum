import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CovoitCreer } from './covoit-creer';

describe('CovoitCreer', () => {
  let component: CovoitCreer;
  let fixture: ComponentFixture<CovoitCreer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CovoitCreer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CovoitCreer);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
