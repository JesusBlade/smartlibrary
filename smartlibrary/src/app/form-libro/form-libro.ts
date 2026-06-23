import { Component, OnInit } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DemoRest } from '../services/demo-rest.service';

@Component({
  selector: 'app-form-libro',
  imports: [CommonModule, FormsModule],
  templateUrl: './form-libro.html',
  styleUrl: './form-libro.css'
})
export class FormLibro implements OnInit {

  autores: any[] = [];
  categorias: any[] = [];

  constructor(private demoRest: DemoRest) { }

  ngOnInit() {
    this.cargarAutores();
    this.cargarCategorias();
  }

  cargarAutores() {
    this.demoRest.getAutores().subscribe({
      next: (data) => {
        this.autores = data;
      },
      error: (err) => console.error(err)
    });
  }

  cargarCategorias() {
    this.demoRest.getCategorias().subscribe({
      next: (data) => {
        this.categorias = data;
      },
      error: (err) => console.error(err)
    });
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Data del formulario:', form.value);

      this.demoRest.saveLibro(form.value).subscribe({
        next: (res) => {
          console.log('Libro registrado:', res);
          form.resetForm();
        },
        error: (err) => console.error(err)
      });
    }
  }
}