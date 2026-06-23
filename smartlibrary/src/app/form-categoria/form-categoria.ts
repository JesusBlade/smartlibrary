import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-form-categoria',
  imports: [CommonModule, FormsModule],
  templateUrl: './form-categoria.html',
  styleUrl: './form-categoria.css'
})
export class FormCategoria {
  onSubmit(form: NgForm) {
    console.log('Data del formulario:', form.value);
  }
}
