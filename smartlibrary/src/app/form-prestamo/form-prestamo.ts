import { Component, OnInit } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DemoRest } from '../services/demo-rest.service';
import { ChangeDetectorRef } from '@angular/core';

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

  constructor(private demoRest: DemoRest, private cdr: ChangeDetectorRef) { }

  // Obtiene el tipo de usuario de la sesión actual
  obtenerTipoUsuario(): string | null {
    return localStorage.getItem('tipoUsuario');
  }

  // Verifica si el usuario autenticado es administrador
  esAdmin(): boolean {
    return this.obtenerTipoUsuario() === 'ADMIN';
  }

  // Verifica si el usuario autenticado es cliente
  esCliente(): boolean {
    return this.obtenerTipoUsuario() === 'CLIENTE';
  }

  ngOnInit() {
    this.cargarUsuarios();
    this.cargarPersonas();
    this.cargarLibros();
    this.cargarPrestamos();
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

  cargarPersonas() {
    this.demoRest.getPersonas().subscribe({
      next: (data) => {
        this.personas = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  cargarLibros() {
    this.demoRest.getLibros().subscribe({
      next: (data) => {
        this.libros = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  cargarPrestamos() {
    this.demoRest.getPrestamos().subscribe({
      next: (data) => {
        this.prestamos = data;
        this.cdr.detectChanges();
      },
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
  
  // Filtra los préstamos de acuerdo al texto de búsqueda y restringe según el rol
  prestamosFiltrados() {
    let prestamosVisibles = this.prestamos;
    
    // Si es un cliente, restringimos la lista para mostrar solo sus propios préstamos
    if (this.esCliente()) {
      const miUsuario = localStorage.getItem('usuario');
      prestamosVisibles = this.prestamos.filter(
        p => this.obtenerUsuario(p.idUsuario) === miUsuario
      );
    }

    if (!this.textoBusqueda) {
      return prestamosVisibles;
    }
    const texto = this.textoBusqueda.toLowerCase();
    return prestamosVisibles.filter((prestamo: any) =>
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
  
  // PROCESO DE NEGOCIO: REGISTRAR LA DEVOLUCIÓN DE UN PRÉSTAMO
  devolver(prestamo: any) {

    // Confirmar la devolución del préstamo
    if (!confirm('¿Registrar la devolución del préstamo?')) {
      return;
    }

    // Llamar al backend para registrar la devolución
    this.demoRest.devolverPrestamo(prestamo.id).subscribe({
      next: (res) => {
        console.log('Préstamo devuelto:', res);

        // Actualizar la tabla de préstamos
        this.cargarPrestamos();
      },
      error: (err) => {
        console.error(err);
        alert('No se pudo registrar la devolución.');
      }
    });
  }


}