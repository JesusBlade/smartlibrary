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

    this.http.get<any[]>('http://localhost:8080/persona').subscribe(data => {
      this.personas = data;
    });
  }

  onSubmit() {
    console.log(this.miForm.value);
    this.http.post('http://localhost:8080/usuario', this.miForm.value).subscribe(res => {
      console.log(res);
    });
  }
}
