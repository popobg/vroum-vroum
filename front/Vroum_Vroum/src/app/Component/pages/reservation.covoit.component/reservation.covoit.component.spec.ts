import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationCovoitComponent } from './reservation.covoit.component';

describe('ReservationCovoitComponent', () => {
  let component: ReservationCovoitComponent;
  let fixture: ComponentFixture<ReservationCovoitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReservationCovoitComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReservationCovoitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
