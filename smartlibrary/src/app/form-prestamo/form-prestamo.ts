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
  

  mostrarFormulario=false;
  textoBusqueda='';
  editando=false;
  idPrestamoEditar=0;
  prestamoEditar:any={};

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

    if (!form.valid) {
      return;
    }
    if (this.editando) {
      this.demoRest.updatePrestamo(this.idPrestamoEditar, form.value).subscribe({
        next: (res) => {
          console.log('Préstamo actualizado:', res);
          this.cargarPrestamos();
          form.resetForm();
		  this.prestamoEditar = {};
          this.mostrarFormulario = false;
          this.editando = false;
          this.idPrestamoEditar = 0;
        },
        error: (err) => console.error(err)
      });
    } else {
      this.demoRest.savePrestamo(form.value).subscribe({
        next: (res) => {
          console.log('Préstamo registrado:', res);
          this.cargarPrestamos();
          form.resetForm();
		  this.prestamoEditar = {};
          this.mostrarFormulario = false;
        },
        error: (err) => console.error(err)
      });
    }
  }
  
  editar(prestamo: any) {
    this.editando = true;
    this.idPrestamoEditar = prestamo.id;
    this.prestamoEditar = { ...prestamo };
    this.mostrarFormulario = true;
  }
  
  eliminar(id: number) {
    if (confirm('¿Está seguro de eliminar este préstamo?')) {
      this.demoRest.deletePrestamo(id).subscribe({
        next: () => {
          this.cargarPrestamos();
        },
        error: (err) => console.error(err)
      });
    }
  }
  
  prestamosFiltrados() {
    if (!this.textoBusqueda) {
      return this.prestamos;
    }
    const texto = this.textoBusqueda.toLowerCase();
    return this.prestamos.filter((prestamo: any) =>
      this.obtenerUsuario(prestamo.idUsuario)
        .toLowerCase()
        .includes(texto)
      ||
      this.obtenerPersona(prestamo.idPersona)
        .toLowerCase()
        .includes(texto)
      ||
      this.obtenerLibro(prestamo.idLibro)
        .toLowerCase()
        .includes(texto)
    );
  }
  
  
  obtenerUsuario(id: number): string {
    const usuario = this.usuarios.find(u => u.id == id);
    return usuario ? usuario.user : '';
  }

  obtenerPersona(id: number): string {
    const persona = this.personas.find(p => p.id == id);
    return persona ? persona.nombre + ' ' + persona.apellido : '';
  }

  obtenerLibro(id: number): string {
    const libro = this.libros.find(l => l.id == id);
    return libro ? libro.titulo : '';
  }
  
  seleccionarUsuario() {
    const usuario = this.usuarios.find(
      u => u.id == this.prestamoEditar.idUsuario
    );
    console.log(usuario);
    if (usuario) {
      this.prestamoEditar.idPersona = usuario.idPersona;
      console.log(this.prestamoEditar.idPersona);
    }
  }
}