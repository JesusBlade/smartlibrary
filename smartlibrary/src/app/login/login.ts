import { Component } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { DemoRest } from '../services/demo-rest.service';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  errorMensaje: string = '';

  constructor(private demoRest: DemoRest, private router: Router) {
    localStorage.removeItem('token');
    localStorage.removeItem('usuario');
    localStorage.removeItem('tipoUsuario');
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      this.errorMensaje = '';
      this.demoRest.login(form.value).subscribe({
        next: (token) => {
          localStorage.setItem('token', token);
          localStorage.setItem('usuario', form.value.user);

          // Obtiene la lista de usuarios para identificar el tipo de usuario (ADMIN o CLIENTE)
          this.demoRest.getUsuarios().subscribe({
            next: (usuarios) => {
              const usuarioEncontrado = usuarios.find(u => u.user === form.value.user);
              if (usuarioEncontrado) {
                // Almacena el tipo de usuario (ADMIN o CLIENTE) en LocalStorage
                localStorage.setItem('tipoUsuario', usuarioEncontrado.tipoUsuario);
                
                // Redirección condicional: los administradores van a gestión de préstamos, clientes al catálogo de libros
                if (usuarioEncontrado.tipoUsuario === 'ADMIN') {
                  this.router.navigate(['/formprestamo']);
                } else {
                  this.router.navigate(['/formlibro']);
                }
              } else {
                // Por defecto si no se encuentra en la lista, se asigna perfil CLIENTE
                localStorage.setItem('tipoUsuario', 'CLIENTE');
                this.router.navigate(['/formlibro']);
              }
            },
            error: (err) => {
              console.error('Error al obtener lista de usuarios:', err);
             
              localStorage.setItem('tipoUsuario', 'CLIENTE');
              this.router.navigate(['/formlibro']);
            }
          });
        },
        error: (err) => {
          console.error(err);
          this.errorMensaje = 'Usuario o contraseña incorrectos';
        }
      });
    }
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('usuario');
    localStorage.removeItem('tipoUsuario');
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }
}
