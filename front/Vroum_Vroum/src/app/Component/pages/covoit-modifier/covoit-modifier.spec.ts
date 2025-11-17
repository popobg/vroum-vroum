import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CovoitModifier } from './covoit-modifier';

describe('CovoitModifier', () => {
  let component: CovoitModifier;
  let fixture: ComponentFixture<CovoitModifier>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CovoitModifier]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CovoitModifier);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
