import { Component, OnInit } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DemoRest } from '../services/demo-rest.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-form-autor',
  imports: [CommonModule, FormsModule],
  templateUrl: './form-autor.html',
  styleUrl: './form-autor.css'
})
export class FormAutor implements OnInit {

  autores: any[] = [];

  mostrarFormulario = false;
  textoBusqueda = '';

  editando = false;
  idAutorEditar = 0;
  autorEditar: any = {};

  constructor(
    private demoRest: DemoRest,
    private cdr: ChangeDetectorRef
  ) {}
  
  ngOnInit() {
      this.cargarAutores();
    }

  cargarAutores() {
    this.demoRest.getAutores().subscribe({
      next: (data) => {
		console.log('Autores:', data);
        this.autores = data;
		this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  abrirModalNuevo() {
    this.editando = false;
    this.idAutorEditar = 0;
    this.autorEditar = {};
    this.mostrarFormulario = true;
  }

  cerrarModal() {
    this.mostrarFormulario = false;
    this.editando = false;
    this.idAutorEditar = 0;
    this.autorEditar = {};
  }

  editar(autor: any) {
    this.editando = true;
    this.idAutorEditar = autor.id;
    this.autorEditar = { ...autor };
    this.mostrarFormulario = true;
  }

  onSubmit(form: NgForm) {
    if (!form.valid) {
      return;
    }
    if (this.editando) {

      this.demoRest.updateAutor(this.idAutorEditar, form.value).subscribe({
        next: (res) => {
          console.log('Autor actualizado:', res);
          this.cargarAutores();
          form.resetForm();
          this.cerrarModal();
        },
        error: (err) => console.error(err)
      });

    } else {

      this.demoRest.saveAutor(form.value).subscribe({
        next: (res) => {
          console.log('Autor registrado:', res);
          this.cargarAutores();
          form.resetForm();
          this.cerrarModal();
        },
        error: (err) => console.error(err)
      });
    }
  }

  autoresFiltrados() {
    if (!this.textoBusqueda) {
      return this.autores;
    }
    return this.autores.filter((autor: any) =>
      autor.nombreAutor
        .toLowerCase()
        .includes(this.textoBusqueda.toLowerCase())
    );
  }

  eliminar(id: number) {
    if (confirm('¿Está seguro de eliminar este autor?')) {
      this.demoRest.deleteAutor(id).subscribe({
        next: () => {
          this.cargarAutores();
        },
        error: (err) => console.error(err)
      });
    }
  }

}