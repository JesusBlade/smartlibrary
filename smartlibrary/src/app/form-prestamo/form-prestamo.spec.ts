import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormPrestamo } from './form-prestamo';

describe('FormPrestamo', () => {
  let component: FormPrestamo;
  let fixture: ComponentFixture<FormPrestamo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormPrestamo]
    }).compileComponents();

    fixture = TestBed.createComponent(FormPrestamo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
