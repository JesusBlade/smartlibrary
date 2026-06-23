import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormAutor } from './form-autor';

describe('FormAutor', () => {
  let component: FormAutor;
  let fixture: ComponentFixture<FormAutor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormAutor]
    }).compileComponents();

    fixture = TestBed.createComponent(FormAutor);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
