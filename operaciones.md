# 📚 API de Biblioteca - Operaciones REST (Sin Base de Datos)

Este proyecto implementa una serie de **endpoints REST** enfocados en operaciones típicas de una biblioteca, utilizando únicamente **cálculos en memoria** (sin conexión a base de datos).

---

## 🚀 Tecnologías

* Java
* Spring Boot 3.5
* REST API (GET con PathVariable)

---

## 📌 Endpoints disponibles

---

### 1. 📄 Calcular multa por retraso

**GET** `/api/multa/calcular/{diasRetraso}/{costoPorDia}/{diasGracia}/{tipoUsuario}`

#### 🔹 Parámetros (PathVariable)

* `diasRetraso`: Días de retraso.
* `costoPorDia`: Costo por día de retraso.
* `diasGracia`: Días sin multa.
* `tipoUsuario`: `ESTUDIANTE` o `DOCENTE` (docentes tienen 50% descuento).

#### 🔹 Ejemplo Real (URL)

```text
http://localhost:8080/api/multa/calcular/5/2.5/1/ESTUDIANTE
```

---

### 2. 📖 Calcular días necesarios para leer un libro

**GET** `/api/lectura/dias/{totalPaginas}/{paginasPorDia}/{dificultad}/{horasDisponibles}`

#### 🔹 Parámetros (PathVariable)

* `totalPaginas`: Total de páginas del libro.
* `paginasPorDia`: Páginas que lee por día.
* `dificultad`: Nivel del libro (1-3).
* `horasDisponibles`: Horas diarias para leer.

#### 🔹 Ejemplo Real (URL)

```text
http://localhost:8080/api/lectura/dias/300/30/1/2.0
```

---

### 3. 📘 Calcular libros que puede leer en un mes

**GET** `/api/lectura/libros-mes/{paginasPorLibro}/{paginasPorDia}/{diasLecturaMes}/{nivelConcentracion}`

#### 🔹 Parámetros (PathVariable)

* `paginasPorLibro`: Promedio de páginas por libro.
* `paginasPorDia`: Páginas leídas por día.
* `diasLecturaMes`: Días dedicados a leer al mes.
* `nivelConcentracion`: Factor de concentración (ej: 1.0).

#### 🔹 Ejemplo Real (URL)

```text
http://localhost:8080/api/lectura/libros-mes/200/20/30/1.0
```

---

### 4. 📚 Calcular libros disponibles

**GET** `/api/libros/disponibles/{totalEjemplares}/{prestados}/{reservados}/{enMantenimiento}`

#### 🔹 Parámetros (PathVariable)

* `totalEjemplares`: Total de copias.
* `prestados`: Cantidad prestada.
* `reservados`: Cantidad reservada.
* `enMantenimiento`: Cantidad en mantenimiento.

#### 🔹 Ejemplo Real (URL)

```text
http://localhost:8080/api/libros/disponibles/10/3/1/1
```

---

### 5. 👤 Calcular puntaje del usuario

**GET** `/api/usuario/puntaje/{librosLeidos}/{entregasATiempo}/{retrasos}/{multasPagadas}`

#### 🔹 Parámetros (PathVariable)

* `librosLeidos`: Total de libros leídos.
* `entregasATiempo`: Devoluciones puntuales.
* `retrasos`: Cantidad de retrasos.
* `multasPagadas`: Total de multas pagadas.

#### 🔹 Ejemplo Real (URL)

```text
http://localhost:8080/api/usuario/puntaje/10/8/2/1
```

---

### 6. ⏳ Calcular días de devolución

**GET** `/api/prestamo/dias-devolucion/{diasBase}/{diasExtra}/{tipoUsuario}/{retrasosPrevios}`

#### 🔹 Parámetros (PathVariable)

* `diasBase`: Días estándar de préstamo.
* `diasExtra`: Días adicionales.
* `tipoUsuario`: `ESTUDIANTE` o `DOCENTE`.
* `retrasosPrevios`: Historial de retrasos.

#### 🔹 Ejemplo Real (URL)

```text
http://localhost:8080/api/prestamo/dias-devolucion/7/3/ESTUDIANTE/0
```

---

### 7. 📊 Calcular progreso de lectura

**GET** `/api/lectura/progreso/{paginasLeidas}/{totalPaginas}/{diasTranscurridos}/{diasPlaneados}`

#### 🔹 Parámetros (PathVariable)

* `paginasLeidas`: Páginas leídas hasta el momento.
* `totalPaginas`: Total de páginas del libro.
* `diasTranscurridos`: Días desde el inicio.
* `diasPlaneados`: Días estimados para terminar.

#### 🔹 Ejemplo Real (URL)

```text
http://localhost:8080/api/lectura/progreso/150/300/5/10
```

---

### 8. 🏛️ Calcular nivel de uso de la biblioteca

**GET** `/api/biblioteca/uso/{usuariosActivos}/{librosPrestados}/{capacidadUsuarios}/{capacidadLibros}`

#### 🔹 Parámetros (PathVariable)

* `usuariosActivos`: Usuarios actuales.
* `librosPrestados`: Libros prestados actualmente.
* `capacidadUsuarios`: Capacidad máxima de usuarios.
* `capacidadLibros`: Capacidad máxima de libros.

#### 🔹 Ejemplo Real (URL)

```text
http://localhost:8080/api/biblioteca/uso/50/120/100/200
```

---

## 📌 Notas

* Todos los endpoints usan método **GET**.
* Los parámetros van **en la URL** como segmentos del path (PathVariable).
* Los cálculos son simulaciones directas de reglas de negocio.

---

## 👨‍💻 Autor

Proyecto desarrollado como práctica de backend con Spring Boot.


