import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Reservation_covoitComponent } from './reservation_covoit.component';

describe('Reservation_covoitComponent', () => {
  let component: Reservation_covoitComponent;
  let fixture: ComponentFixture<Reservation_covoitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Reservation_covoitComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Reservation_covoitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
