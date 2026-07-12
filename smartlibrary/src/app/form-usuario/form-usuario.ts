import { Component, OnInit } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DemoRest } from '../services/demo-rest.service';


@Component({
  selector: 'app-form-usuario',
  imports: [CommonModule, FormsModule],
  templateUrl: './form-usuario.html',
  styleUrl: './form-usuario.css'
})
export class FormUsuario implements OnInit {

  personas: any[] = [];

  constructor(private demoRest: DemoRest) { }

  ngOnInit() {
    this.cargarPersonas();
  }

  cargarPersonas() {
    this.demoRest.getPersonas().subscribe({
      next: (data) => this.personas = data,
      error: (err) => console.error(err)
    });
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Data del formulario:', form.value);

      this.demoRest.saveUsuario(form.value).subscribe({
        next: (res) => {
          console.log('Usuario registrado:', res);
          form.resetForm();
        },
        error: (err) => console.error(err)
      });
    }
  }
}
