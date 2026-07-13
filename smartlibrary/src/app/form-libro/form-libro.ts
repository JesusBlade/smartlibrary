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
  libros: any[] = [];
  
  mostrarFormulario = false;
  textoBusqueda = '';
  editando = false;
  idLibroEditar = 0;
  libroEditar: any = {};
  
  mostrarAutor = false;
  mostrarCategoria = false;
  
  autorNuevo = {
    nombreAutor: '',
    nacionalidad: ''
  };

  categoriaNueva = {
    categoria: ''
  };
  

  constructor(private demoRest: DemoRest) { }

  ngOnInit() {
    this.cargarAutores();
    this.cargarCategorias();
	this.cargarLibros();
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
  
  cargarLibros() {
    this.demoRest.getLibros().subscribe({
      next: (data) => {
        this.libros = data;
      },
      error: (err) => console.error(err)
    });
  }
  obtenerAutor(id: number): string {
    const autor = this.autores.find(a => a.id == id);
    return autor ? autor.nombreAutor : '';
  }

  obtenerCategoria(id: number): string {
    const categoria = this.categorias.find(c => c.id == id);
    return categoria ? categoria.categoria : '';
  }
  
  editar(libro: any) {

    this.editando = true;

    this.idLibroEditar = libro.id;

    this.libroEditar = { ...libro };

    this.mostrarFormulario = true;

  }

  onSubmit(form: NgForm) {
	console.log(form.value);
    if (!form.valid) {
      return;
    }
    if (this.editando) {
      this.demoRest.updateLibro(this.idLibroEditar, form.value).subscribe({
        next: (res) => {
          console.log('Libro actualizado:', res);
          this.cargarLibros();
          form.resetForm();
		  this.libroEditar = {};
          this.mostrarFormulario = false;
          this.editando = false;
          this.idLibroEditar = 0;
        },
        error: (err) => console.error(err)
      });
    } else {
      this.demoRest.saveLibro(form.value).subscribe({
        next: (res) => {
		console.log(form.value);
          console.log('Libro registrado:', res);
          this.cargarLibros();
          form.resetForm();
		  this.libroEditar = {};
          this.mostrarFormulario = false;
        },
        error: (err) => console.error(err)
      });
    }
  }
  
  librosFiltrados() {

    if (!this.textoBusqueda) {
      return this.libros;
    }

    return this.libros.filter((libro: any) =>
      libro.titulo.toLowerCase()
        .includes(this.textoBusqueda.toLowerCase())
    );

  }
  
  eliminar(id: number) {
    if (confirm('¿Está seguro de eliminar este libro?')) {
      this.demoRest.deleteLibro(id).subscribe({
        next: () => {
          this.cargarLibros();
        },
        error: (err) => console.error(err)
      });
    }
  }
  
  guardarAutor() {
    this.demoRest.saveAutor(this.autorNuevo).subscribe({
      next: () => {
        this.cargarAutores();
        this.mostrarAutor = false;
        this.autorNuevo = {
          nombreAutor: '',
          nacionalidad: ''
        };
      },
      error: (err) => console.error(err)
    });
  }

  guardarCategoria() {
    this.demoRest.saveCategoria(this.categoriaNueva).subscribe({
      next: () => {
        this.cargarCategorias();
        this.mostrarCategoria = false;
        this.categoriaNueva = {
          categoria: ''
        };
      },

      error: (err) => console.error(err)
    });
  }
}