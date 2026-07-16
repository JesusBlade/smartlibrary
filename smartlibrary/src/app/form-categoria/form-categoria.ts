import { Component, OnInit } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DemoRest } from '../services/demo-rest.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-form-categoria',
  imports: [CommonModule, FormsModule],
  templateUrl: './form-categoria.html',
  styleUrl: './form-categoria.css'
})
export class FormCategoria implements OnInit {

  categorias: any[] = [];

  mostrarFormulario = false;
  textoBusqueda = '';

  editando = false;
  idCategoriaEditar = 0;
  categoriaEditar: any = {};

  constructor(private demoRest: DemoRest, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.cargarCategorias();
  }

  cargarCategorias() {
    this.demoRest.getCategorias().subscribe({
      next: (data) => {
        this.categorias = data;
		this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  editar(categoria: any) {
    this.editando = true;
    this.idCategoriaEditar = categoria.id;
    this.categoriaEditar = { ...categoria };
    this.mostrarFormulario = true;
  }

  onSubmit(form: NgForm) {
    if (!form.valid) {
      return;
    }
    if (this.editando) {
      this.demoRest.updateCategoria(this.idCategoriaEditar, form.value).subscribe({
        next: (res) => {
          console.log('Categoría actualizada:', res);
          this.cargarCategorias();
          form.resetForm();
          this.categoriaEditar = {};
          this.mostrarFormulario = false;
          this.editando = false;
          this.idCategoriaEditar = 0;
        },
        error: (err) => console.error(err)
      });
    } else {
      this.demoRest.saveCategoria(form.value).subscribe({
        next: (res) => {
          console.log('Categoría registrada:', res);
          this.cargarCategorias();
          form.resetForm();
          this.categoriaEditar = {};
          this.mostrarFormulario = false;
        },
        error: (err) => console.error(err)
      });
    }
  }

  categoriasFiltradas() {
    if (!this.textoBusqueda) {
      return this.categorias;
    }
    return this.categorias.filter((categoria: any) =>
      categoria.categoria
        .toLowerCase()
        .includes(this.textoBusqueda.toLowerCase())
    );
  }

  eliminar(id: number) {
    if (confirm('¿Está seguro de eliminar esta categoría?')) {
      this.demoRest.deleteCategoria(id).subscribe({
        next: () => {
          this.cargarCategorias();
        },
        error: (err) => console.error(err)
      });
    }
  }

}