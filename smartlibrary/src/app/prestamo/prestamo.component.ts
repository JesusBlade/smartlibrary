import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-prestamo',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './prestamo.component.html'
})
export class PrestamoComponent implements OnInit {
  miForm!: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient) {}

  ngOnInit() {
    this.miForm = this.fb.group({
      idUsuario: ['', Validators.required],
      idPersona: ['', Validators.required],
      idLibro: ['', Validators.required],
      multa: ['', Validators.required]
    });
  }

  onSubmit() {
    console.log(this.miForm.value);
    this.http.post('http://localhost:8080/prestamo', this.miForm.value).subscribe();
  }
}
