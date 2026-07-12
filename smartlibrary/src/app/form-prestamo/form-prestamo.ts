import { Component, OnInit } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DemoRest } from '../services/demo-rest.service';

@Component({
  selector: 'app-form-prestamo',
  imports: [CommonModule, FormsModule],
  templateUrl: './form-prestamo.html',
  styleUrl: './form-prestamo.css'
})
export class FormPrestamo implements OnInit {

  usuarios: any[] = [];
  personas: any[] = [];
  libros: any[] = [];
  prestamos: any[] = [];

  constructor(private demoRest: DemoRest) { }

  ngOnInit() {
    this.cargarUsuarios();
    this.cargarPersonas();
    this.cargarLibros();
	this.cargarPrestamos();
  }

  cargarUsuarios() {
    this.demoRest.getUsuarios().subscribe({
      next: (data) => this.usuarios = data,
      error: (err) => console.error(err)
    });
  }

  cargarPersonas() {
    this.demoRest.getPersonas().subscribe({
      next: (data) => this.personas = data,
      error: (err) => console.error(err)
    });
  }

  cargarLibros() {
    this.demoRest.getLibros().subscribe({
      next: (data) => this.libros = data,
      error: (err) => console.error(err)
    });
  }
  
  cargarPrestamos() {
    this.demoRest.getPrestamos().subscribe({
      next: (data) => this.prestamos = data,
      error: (err) => console.error(err)
    });
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Data del formulario:', form.value);

      this.demoRest.savePrestamo(form.value).subscribe({
        next: (res) => {
          console.log('Préstamo registrado:', res);
          form.resetForm();
        },
        error: (err) => console.error(err)
      });
    }
  }
}