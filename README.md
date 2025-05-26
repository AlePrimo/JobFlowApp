# 💼 JobFlowApp – Plataforma de Gestión de Búsqueda Laboral

**JobFlowApp** es una aplicación backend desarrollada en Java con Spring Boot que permite gestionar usuarios, compañías, ofertas laborales y postulaciones, con funcionalidades completas de carga de CV y control de acceso por roles.

---

## 🧹 Tecnologías utilizadas

* ☕ Java 17
* 🧱 Spring Boot
* 🔐 Spring Security (OAuth2 configurado)
* 💃 MySQL
* 📄 Spring Data JPA + Hibernate
* 🧪 Validaciones con Jakarta Validation
* 🛆 Maven
* 📂 Swagger/OpenAPI 3
* 📄 Carga y descarga de archivos (CVs)
* 🔐 OAuth2 Login con Google *(configurado, pero desactivado en esta versión)*

---

## 🔧 Funcionalidades principales

### 👤 Usuarios

* Crear usuario
* Ver listado y detalles
* Cargar y descargar CV (archivo `.pdf`)
* Postularse a empleos

### 🏢 Compañías

* Crear compañía
* Crear, editar y eliminar ofertas de trabajo
* Ver postulaciones recibidas
* Cambiar estado de una postulación

### 🧾 Postulaciones

* Los usuarios pueden postularse a ofertas.
* Las compañías pueden ver y actualizar el estado de las postulaciones a sus ofertas.

---

## 🔐 Seguridad

* Está **configurada** seguridad basada en roles (`USER`, `COMPANY`) mediante OAuth2 con Google.
* Actualmente se encuentra **deshabilitada para facilitar pruebas manuales desde Postman**.
* Toda la estructura está lista para activarla fácilmente cuando se desee.

---

## 🧪 Pruebas y validaciones

✔️ Todos los endpoints fueron probados manualmente con Postman.
✔️ DTOs validados con anotaciones de Jakarta (`@NotBlank`, `@Email`, `@Past`, etc.).
✔️ Control de relaciones y recursividad mediante `@JsonIgnore`, `@JsonManagedReference` y `@JsonBackReference`.

---

## 📁 Estructura del proyecto

```
JobFlowApp/
│
├── config/              # Configuraciones de seguridad, CORS, OAuth
├── controllers/         # Controladores REST (User, Company, Job, Application)
├── controllers/dtos/    # DTOs de cada entidad + DTOs Lite
├── models/              # Entidades JPA
├── repositories/        # Interfaces JpaRepository
├── services/            # Interfaces y clases de servicio
├── daos/                # Clases DAO con lógica de persistencia
├── uploads/             # Carpeta para guardar archivos CV
└── application.properties  # Configuraciones generales del proyecto
```

---

## 🚀 Cómo ejecutar

1. Crear una base de datos MySQL llamada `job_app`.
2. Configurar usuario y contraseña de MySQL como variables de entorno:
   `USERMYSQL` y `PASSMYSQL`.
3. Ejecutar el proyecto desde tu IDE o con `mvn spring-boot:run`.
4. Acceder a la documentación en:
   👉 [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

---

## 📌 Notas finales

* Este proyecto es **backend-only**, pensado para ser probado mediante herramientas como Postman o Swagger.
* Toda la lógica está preparada para agregar frontend en el futuro si se desea.
* Se puede activar la seguridad simplemente habilitando el filtro en `SecurityConfig.java`.

---

## 🧑‍💼 Autor

Desarrollado por **Alejandro Primo** como parte de su portfolio backend Java.
📁 Repositorio: [github.com/AlePrimo/JobFlowApp](https://github.com/AlePrimo/JobFlowApp)

---
