import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormPersona } from './form-persona';

describe('FormPersona', () => {
  let component: FormPersona;
  let fixture: ComponentFixture<FormPersona>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormPersona]
    }).compileComponents();

    fixture = TestBed.createComponent(FormPersona);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
