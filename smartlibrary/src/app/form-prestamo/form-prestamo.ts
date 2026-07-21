import { Component, OnInit } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DemoRest } from '../services/demo-rest.service';
import { ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

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

  // Variable para controlar el tipo de vista: 'todos', 'vencidos', 'multas'
  vista: string = 'todos';

  mostrarFormulario=false;
  textoBusqueda='';
  editando=false;
  idPrestamoEditar=0;
  prestamoEditar:any={};

  constructor(private demoRest: DemoRest, private cdr: ChangeDetectorRef, private route: ActivatedRoute) { }

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

  // Obtiene el título de la vista actual según el tipo de filtro
  obtenerTituloVista(): string {
    if (this.vista === 'vencidos') {
      return 'Mis Préstamos Vencidos';
    } else if (this.vista === 'multas') {
      return 'Mis Multas';
    } else {
      return this.esAdmin() ? 'Gestión de Préstamos' : 'Mis Préstamos';
    }
  }

  // Obtiene el subtítulo de la vista actual según el tipo de filtro
  obtenerSubtituloVista(): string {
    if (this.vista === 'vencidos') {
      return 'Consulta tus préstamos que han excedido la fecha límite de devolución';
    } else if (this.vista === 'multas') {
      return 'Consulta las multas pendientes de pago por préstamos retrasados';
    } else {
      return this.esAdmin() ? 'Gestiona los préstamos y devoluciones de la biblioteca' : 'Consulta el estado de tus préstamos personales';
    }
  }

  ngOnInit() {
    // Detectar el parámetro de ruta para determinar la vista
    this.route.params.subscribe(params => {
      if (params['vista']) {
        this.vista = params['vista'];
      } else {
        this.vista = 'todos';
      }
    });

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
  
  // PROCESO DE NEGOCIO:
  // CONSULTAR LOS PRÉSTAMOS DEL CLIENTE AUTENTICADO
  // Obtiene los préstamos filtrados según el tipo de vista y el rol del usuario
  prestamosFiltrados() {
    let prestamosVisibles = this.prestamos;

    // Si es un cliente, restringimos la lista para mostrar solo sus propios préstamos
    if (this.esCliente()) {
      const miUsuario = localStorage.getItem('usuario');
      prestamosVisibles = this.prestamos.filter(
        p => this.obtenerUsuario(p.idUsuario) === miUsuario
      );
    }

    // Aplicar filtro según el tipo de vista
    if (this.vista === 'vencidos') {
      prestamosVisibles = this.filtrarPrestamosVencidos(prestamosVisibles);
    } else if (this.vista === 'multas') {
      prestamosVisibles = this.filtrarPrestamosConMultas(prestamosVisibles);
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

  // PROCESO DE NEGOCIO:
  // CONSULTAR PRÉSTAMOS VENCIDOS DEL CLIENTE
  // Filtra los préstamos activos cuya fecha límite de devolución ya venció
  filtrarPrestamosVencidos(prestamos: any[]): any[] {
    const fechaActual = new Date();
    return prestamos.filter((prestamo: any) => {
      // Condición: estado = ACTIVO y fecha_devolucion < fecha actual
      const esActivo = prestamo.estado === 'ACTIVO';
      const fechaDevolucion = new Date(prestamo.fechaDevolucion);
      const estaVencido = fechaDevolucion < fechaActual;
      return esActivo && estaVencido;
    });
  }

  // PROCESO DE NEGOCIO:
  // CONSULTAR MULTAS PENDIENTES DEL CLIENTE
  // Obtiene los préstamos que tienen multas pendientes de pago (multas reales y estimadas)
  filtrarPrestamosConMultas(prestamos: any[]): any[] {
    return prestamos.filter((prestamo: any) => {
      const tipoMulta = this.obtenerTipoMulta(prestamo);
      // Incluir multas reales (devueltas con multa > 0) y multas estimadas (activas y vencidas)
      return tipoMulta === 'REAL' || tipoMulta === 'ESTIMADA';
    });
  }

  // PROCESO DE NEGOCIO:
  // CALCULAR DÍAS DE RETRASO DE UN PRÉSTAMO
  // Calcula los días de retraso de un préstamo vencido respecto a la fecha actual
  calcularDiasRetraso(fechaDevolucion: string): number {
    const fechaLimite = new Date(fechaDevolucion);
    const fechaActual = new Date();
    const diferenciaTiempo = fechaActual.getTime() - fechaLimite.getTime();
    const diasRetraso = Math.ceil(diferenciaTiempo / (1000 * 3600 * 24));
    return diasRetraso > 0 ? diasRetraso : 0;
  }

  // PROCESO DE NEGOCIO:
  // CALCULAR MULTA ESTIMADA PARA PRÉSTAMOS VENCIDOS
  // Calcula la multa estimada dinámicamente para préstamos activos que ya vencieron
  // Fórmula: (fecha actual - fecha límite devolución) × S/ 5.00
  calcularMultaEstimada(fechaDevolucion: string): number {
    const diasRetraso = this.calcularDiasRetraso(fechaDevolucion);
    const MULTA_POR_DIA = 5.0;
    return diasRetraso * MULTA_POR_DIA;
  }

  // PROCESO DE NEGOCIO:
  // DETERMINAR EL TIPO DE MULTA (REAL O ESTIMADA)
  // Determina si la multa es REAL (ya devuelto y guardada en BD) o ESTIMADA (aún activo y vencido)
  obtenerTipoMulta(prestamo: any): string {
    // Si el préstamo está devuelto y tiene multa > 0, es una multa REAL
    if (prestamo.estado === 'DEVUELTO' && prestamo.multa && prestamo.multa > 0) {
      return 'REAL';
    }
    // Si el préstamo está activo, vencido y aún no devuelto, es una multa ESTIMADA
    if (prestamo.estado === 'ACTIVO') {
      const fechaDevolucion = new Date(prestamo.fechaDevolucion);
      const fechaActual = new Date();
      if (fechaDevolucion < fechaActual) {
        return 'ESTIMADA';
      }
    }
    return '';
  }

  // PROCESO DE NEGOCIO:
  // OBTENER EL MONTO DE MULTA (REAL O ESTIMADA)
  // Retorna el monto de la multa: si es REAL usa el valor de BD, si es ESTIMADA lo calcula dinámicamente
  obtenerMontoMulta(prestamo: any): number {
    const tipoMulta = this.obtenerTipoMulta(prestamo);
    if (tipoMulta === 'REAL') {
      return prestamo.multa;
    } else if (tipoMulta === 'ESTIMADA') {
      return this.calcularMultaEstimada(prestamo.fechaDevolucion);
    }
    return 0;
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