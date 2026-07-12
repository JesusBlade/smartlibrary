import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DemoRest } from '../services/demo-rest.service';

@Component({
  selector: 'app-form-autor',
  imports: [CommonModule, FormsModule],
  templateUrl: './form-autor.html',
  styleUrl: './form-autor.css'
})
export class FormAutor {

  constructor(private demoRest: DemoRest) {}

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Data del formulario:', form.value);

      this.demoRest.saveAutor(form.value).subscribe({
        next: (res) => {
          console.log('Autor guardado:', res);
          form.resetForm();
        },
        error: (err) => console.error(err)
      });
    }
  }
}
