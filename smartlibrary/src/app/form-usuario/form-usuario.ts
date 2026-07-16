import { Component, OnInit } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DemoRest } from '../services/demo-rest.service';

import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-form-usuario',
  imports: [CommonModule, FormsModule],
  templateUrl: './form-usuario.html',
  styleUrl: './form-usuario.css'
})
export class FormUsuario implements OnInit {

  personas: any[] = [];
  usuarios: any[] = [];

  mostrarFormulario = false;
  mostrarPersona = false;

  textoBusqueda = '';


  personaNueva = {
    nombre: '',
    apellido: '',
    dni: '',
    correo: ''
  };

  constructor(private demoRest: DemoRest, private cdr: ChangeDetectorRef) { }

  ngOnInit() {
    this.cargarPersonas();
    this.cargarUsuarios();
  }

  cargarPersonas() {
    this.demoRest.getPersonas().subscribe({
      next: (data) => {
        this.personas = data;
		this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  cargarUsuarios() {
    this.demoRest.getUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data;
		this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  usuariosFiltrados() {

    if (!this.textoBusqueda) {
      return this.usuarios;
    }

    return this.usuarios.filter((u: any) =>
      u.user.toLowerCase()
        .includes(this.textoBusqueda.toLowerCase())
    );

  }

  obtenerPersona(idPersona: number): string {

    const persona = this.personas.find(
      p => p.id == idPersona
    );

    if (!persona) {
      return '';
    }

    return persona.nombre + ' ' + persona.apellido;
  }


  guardarPersona() {

    console.log('ENTRO A GUARDAR');

    console.log(this.personaNueva);

    this.demoRest.savePersona(this.personaNueva).subscribe({

      next: () => {

        console.log('GUARDÓ CORRECTAMENTE');

        this.cargarPersonas();

        this.personaNueva = {
          nombre: '',
          apellido: '',
          dni: '',
          correo: ''
        };

      },

      error: (err) => {
        console.error('ERROR BACKEND', err);
      }

    });

  }

  onSubmit(form: NgForm) {

    if (form.valid) {

      console.log('Data del formulario:', form.value);

      this.demoRest.saveUsuario(form.value).subscribe({

        next: (res) => {

          console.log('Usuario registrado:', res);

          this.cargarUsuarios();

          form.resetForm();

          this.mostrarFormulario = false;

        },

        error: (err) => console.error(err)

      });

    }

  }

}