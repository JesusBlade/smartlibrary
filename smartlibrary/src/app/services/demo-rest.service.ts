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
  
  updateLibro(id: number, libro: any) {
      return this.http.put<any>(`${this.baseUrl}/libro/${id}`, libro);
    }

  deleteLibro(id: number) {
      return this.http.delete(`${this.baseUrl}/libro/${id}`);
    }

  // PRESTAMO
  getPrestamos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/prestamo`);
  }
  savePrestamo(prestamo: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/prestamo`, prestamo);
  }
  
  updatePrestamo(id: number, prestamo: any) {
    return this.http.put<any>(`${this.baseUrl}/prestamo/${id}`, prestamo);
  }

  deletePrestamo(id: number) {
    return this.http.delete(`${this.baseUrl}/prestamo/${id}`);
  }

  // AUTOR
  getAutores(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/autores`);
  }
  saveAutor(autor: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/autores`, autor);
  }

  // CATEGORIA
  getCategorias(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/categorias`);
  }
  saveCategoria(categoria: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/categorias`, categoria);
  }
  
}
