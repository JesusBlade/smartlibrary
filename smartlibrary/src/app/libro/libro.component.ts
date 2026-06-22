import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-libro',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './libro.component.html'
})
export class LibroComponent implements OnInit {
  miForm!: FormGroup;

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
  }

  onSubmit() {
    console.log(this.miForm.value);
    this.http.post('http://localhost:8080/libro', this.miForm.value).subscribe();
  }
}
