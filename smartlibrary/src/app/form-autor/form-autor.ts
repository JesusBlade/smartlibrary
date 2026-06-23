import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-form-autor',
  imports: [CommonModule, FormsModule],
  templateUrl: './form-autor.html',
  styleUrl: './form-autor.css'
})
export class FormAutor {
  onSubmit(form: NgForm) {
    console.log('Data del formulario:', form.value);
  }
}
