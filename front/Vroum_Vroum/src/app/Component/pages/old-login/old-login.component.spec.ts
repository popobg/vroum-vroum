import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OldLoginComponent } from './old-login.component';

describe('OldLoginComponent', () => {
  let component: OldLoginComponent;
  let fixture: ComponentFixture<OldLoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OldLoginComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OldLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
