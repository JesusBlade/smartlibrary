import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-persona',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './persona.component.html'
})
export class PersonaComponent implements OnInit {
  miForm!: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient) {}

  ngOnInit() {
    this.miForm = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      dni: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]]
    });
  }

  onSubmit() {
    console.log(this.miForm.value);
    this.http.post('URL_BACKEND/persona', this.miForm.value).subscribe(res => {
      console.log(res);
    });
  }
}
