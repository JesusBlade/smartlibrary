import { Component, signal } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('smartlibrary');

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  // Obtiene el tipo de usuario almacenado en la sesión (LocalStorage)
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

  // Proceso de logout: limpia la sesión de LocalStorage y redirige al login
  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('usuario');
    localStorage.removeItem('tipoUsuario');
    window.location.href = '/login';
  }
}