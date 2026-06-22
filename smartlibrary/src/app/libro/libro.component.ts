import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-libro',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './libro.component.html'
})
export class LibroComponent implements OnInit {
  miForm!: FormGroup;
  autores: any[] = [];
  categorias: any[] = [];

  constructor(private fb: FormBuilder, private http: HttpClient) {}

  ngOnInit() {
    this.miForm = this.fb.group({
      idAutor: ['', Validators.required],
      idCategoria: ['', Validators.required],
      titulo: ['', Validators.required],
      editorial: ['', Validators.required],
      paginas: ['', Validators.required],
      lanzamiento: ['', Validators.required]
    });

    this.http.get<any[]>('http://localhost:8080/autor').subscribe(data => {
      this.autores = data;
    });
    this.http.get<any[]>('http://localhost:8080/categoria').subscribe(data => {
      this.categorias = data;
    });
  }

  onSubmit() {
    console.log(this.miForm.value);
    this.http.post('http://localhost:8080/libro', this.miForm.value).subscribe(res => {
      console.log(res);
    });
  }
}
