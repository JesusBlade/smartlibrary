import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DemoRest } from '../services/demo-rest.service';

@Component({
  selector: 'app-form-persona',
  imports: [CommonModule, FormsModule],
  templateUrl: './form-persona.html',
  styleUrl: './form-persona.css'
})
export class FormPersona {
  constructor(private demoRest: DemoRest) {}

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Data del formulario:', form.value);
      this.demoRest.savePersona(form.value).subscribe({
        next: (res) => {
          console.log('Persona registrada con éxito:', res);
          form.resetForm();
        },
        error: (err) => {
          console.error('Error al registrar persona:', err);
        }
      });
    }
  }
}
