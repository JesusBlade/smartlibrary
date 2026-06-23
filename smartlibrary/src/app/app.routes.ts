import { Routes } from '@angular/router';
import { FormPersona } from './form-persona/form-persona';
import { FormUsuario } from './form-usuario/form-usuario';
import { FormLibro } from './form-libro/form-libro';
import { FormPrestamo } from './form-prestamo/form-prestamo';
import { FormAutor } from './form-autor/form-autor';
import { FormCategoria } from './form-categoria/form-categoria';

export const routes: Routes = [
  { path: 'formpersona', component: FormPersona },
  { path: 'formusuario', component: FormUsuario },
  { path: 'formlibro', component: FormLibro },
  { path: 'formprestamo', component: FormPrestamo },
  { path: 'formautor', component: FormAutor },
  { path: 'formcategoria', component: FormCategoria }
];
