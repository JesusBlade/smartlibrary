import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-usuario',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './usuario.component.html'
})
export class UsuarioComponent implements OnInit {
  miForm!: FormGroup;
  personas: any[] = [];

  constructor(private fb: FormBuilder, private http: HttpClient) {}

  ngOnInit() {
    this.miForm = this.fb.group({
      idPersona: ['', Validators.required],
      user: ['', Validators.required],
      password: ['', Validators.required],
      tipoUsuario: ['', Validators.required]
    });

    this.http.get<any[]>('URL_BACKEND/persona').subscribe(data => {
      this.personas = data;
    });
  }

  onSubmit() {
    console.log(this.miForm.value);
    this.http.post('URL_BACKEND/usuario', this.miForm.value).subscribe(res => {
      console.log(res);
    });
  }
}
