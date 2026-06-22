import { Routes } from '@angular/router';
import { PersonaComponent } from './persona/persona.component';
import { UsuarioComponent } from './usuario/usuario.component';
import { LibroComponent } from './libro/libro.component';
import { PrestamoComponent } from './prestamo/prestamo.component';

export const routes: Routes = [
  { path: 'persona', component: PersonaComponent },
  { path: 'usuario', component: UsuarioComponent },
  { path: 'libro', component: LibroComponent },
  { path: 'prestamo', component: PrestamoComponent }
];
