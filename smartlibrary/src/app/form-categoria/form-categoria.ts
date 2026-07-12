import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DemoRest } from '../services/demo-rest.service';

@Component({
  selector: 'app-form-categoria',
  imports: [CommonModule, FormsModule],
  templateUrl: './form-categoria.html',
  styleUrl: './form-categoria.css'
})
export class FormCategoria {

  constructor(private demoRest: DemoRest) {}

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Data del formulario:', form.value);

      this.demoRest.saveCategoria(form.value).subscribe({
        next: (res) => {
          console.log('Categoría guardada:', res);
          form.resetForm();
        },
        error: (err) => console.error(err)
      });
    }
  }
}
