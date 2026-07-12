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
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      this.errorMensaje = '';
      this.demoRest.login(form.value).subscribe({
        next: (token) => {
          localStorage.setItem('token', token);
          localStorage.setItem('usuario', form.value.user);
          this.router.navigate(['/formprestamo']);
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
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }
}
