import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DemoRest {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  login(data: any): Observable<string> {
    return this.http.post(`${this.baseUrl}/auth`, data, { responseType: 'text' });
  }

  // PERSONA
  getPersonas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/persona`);
  }
  savePersona(persona: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/persona`, persona);
  }

  // USUARIO
  getUsuarios(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/usuario`);
  }
  saveUsuario(usuario: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/usuario`, usuario);
  }

  // LIBRO
  getLibros(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/libro`);
  }
  saveLibro(libro: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/libro`, libro);
  }

  // PRESTAMO
  getPrestamos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/prestamo`);
  }
  savePrestamo(prestamo: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/prestamo`, prestamo);
  }

  // AUTOR
  getAutores(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/autor`);
  }
  saveAutor(autor: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/autor`, autor);
  }

  // CATEGORIA
  getCategorias(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/categoria`);
  }
  saveCategoria(categoria: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/categoria`, categoria);
  }
}
