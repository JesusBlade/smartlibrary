import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-prestamo',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './prestamo.component.html'
})
export class PrestamoComponent implements OnInit {
  miForm!: FormGroup;
  usuarios: any[] = [];
  personas: any[] = [];
  libros: any[] = [];

  constructor(private fb: FormBuilder, private http: HttpClient) {}

  ngOnInit() {
    this.miForm = this.fb.group({
      idUsuario: ['', Validators.required],
      idPersona: ['', Validators.required],
      idLibro: ['', Validators.required],
      multa: ['', Validators.required]
    });

    this.http.get<any[]>('URL_BACKEND/usuario').subscribe(data => {
      this.usuarios = data;
    });
    this.http.get<any[]>('URL_BACKEND/persona').subscribe(data => {
      this.personas = data;
    });
    this.http.get<any[]>('URL_BACKEND/libro').subscribe(data => {
      this.libros = data;
    });
  }

  onSubmit() {
    console.log(this.miForm.value);
    this.http.post('URL_BACKEND/prestamo', this.miForm.value).subscribe(res => {
      console.log(res);
    });
  }
}
