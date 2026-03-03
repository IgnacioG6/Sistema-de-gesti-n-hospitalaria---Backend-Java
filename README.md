# 🏥 Sistema de gestión hospitalaria API

Sistema de gestión hospitalaria construido como API REST con Spring Boot. Maneja pacientes, doctores, citas, historias clínicas, recetas y facturas, con persistencia en PostgreSQL mediante JPA.

---

## 📦 Tech Stack

| Tecnología | Uso |
|---|---|
| Java 21 | Lenguaje |
| Spring Boot 3.x | Framework principal |
| Spring Data JPA / Hibernate | ORM y persistencia |
| PostgreSQL | Base de datos |
| Lombok | Boilerplate reducido |
| Jakarta Validation | Bean Validation en DTOs |
| Springdoc OpenAPI | Documentación automática |

---

## 🏗️ Arquitectura

```
Controllers  →  Services  →  Repositories (JPA)  →  PostgreSQL
   (API)       (lógica +        (queries)           (8 tablas)
              flujos automáticos)
```

El proyecto sigue una arquitectura en capas con clara separación de responsabilidades:

- **Controllers** — Reciben las peticiones HTTP, delegan al service y retornan la respuesta. No contienen lógica de negocio.
- **Services** — Contienen la lógica de negocio, validaciones y flujos automáticos. Cada método tiene una única responsabilidad.
- **Mappers** — Encapsulan la conversión entre DTOs y entidades JPA en ambas direcciones.
- **Repositories** — Interfaces de Spring Data JPA que generan queries automáticamente.
- **Entidades JPA** — Representan las tablas de la base de datos con relaciones y anotaciones de persistencia.

---

## 🗂️ Estructura del Proyecto

```
src/main/java/com/example/hospital/
├── controller/          # Endpoints REST
├── service/             # Lógica de negocio
├── repository/          # Interfaces JPA
├── model/               # Entidades JPA
│   └── enums/           # Enumeraciones del dominio
├── mapper/              # Conversión DTO ↔ Entidad
├── dto/
│   ├── request/         # DTOs de entrada con validaciones
│   └── response/        # DTOs de salida
└── exception/           # Excepciones personalizadas y GlobalExceptionHandler

```

---

## 📋 Módulos

### Pacientes
Gestión completa de pacientes con estados ACTIVO/INACTIVO.

### Doctores
Registro de doctores con especialidad, horario de atención y disponibilidad.

### Citas
Agenda de citas con máquina de estados y validaciones de horario:

```
PROGRAMADO → EN_PROCESO → COMPLETADO
     ↓            ↓
  CANCELADO    CANCELADO
```

**Validaciones automáticas:**
- Límite de 3 citas programadas por paciente
- Sin superposición de horarios del doctor (considera duración)
- No se permite agendar los domingos
- Solo doctores disponibles y pacientes activos

**Flujo automático:** Al completar una cita se crean automáticamente una Historia Clínica y una Factura con valores por defecto, editables después mediante PUT.

### Historias Clínicas
Registros médicos asociados a citas completadas. Soportan creación manual y automática.

### Recetas
Prescripciones de medicamentos asociadas a historias clínicas, con fecha de validez.

### Facturas
Facturación con máquina de estados y cálculo automático de totales:

```
PENDIENTE → PAGADO
    ↓    ↘
VENCIDO   CANCELADO
  ↓    ↘
PAGADO  CANCELADO
```

- Número de factura autogenerado (`FAC-00001`, `FAC-00002`, ...)
- Subtotal y total calculados automáticamente (`@Transient`)
- Validación de descuento contra subtotal

---

## 🔌 Endpoints

### Pacientes
| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/pacientes` | Listar pacientes |
| GET | `/api/pacientes/{id}` | Buscar por ID |
| GET | `/api/pacientes/dni/{dni}` | Buscar por DNI |
| POST | `/api/pacientes` | Crear paciente |
| PUT | `/api/pacientes/{id}` | Actualizar paciente |
| PUT | `/api/pacientes/{id}/activar` | Activar paciente |
| PUT | `/api/pacientes/{id}/desactivar` | Desactivar paciente |
| DELETE | `/api/pacientes/{id}` | Eliminar paciente |

### Doctores
| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/doctores` | Listar doctores |
| GET | `/api/doctores/{id}` | Buscar por ID |
| GET | `/api/doctores/especialidad/{especialidad}` | Buscar por especialidad |
| POST | `/api/doctores` | Registrar doctor |
| PUT | `/api/doctores/{id}` | Actualizar doctor |
| PUT | `/api/doctores/{id}/activar` | Activar disponibilidad |
| PUT | `/api/doctores/{id}/desactivar` | Desactivar disponibilidad |
| DELETE | `/api/doctores/{id}` | Eliminar doctor |

### Citas
| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/cita` | Listar citas |
| GET | `/api/cita/{id}` | Buscar por ID |
| GET | `/api/cita/pacientes/{id}` | Buscar por paciente |
| GET | `/api/cita/doctores/{id}` | Buscar por doctor |
| GET | `/api/cita/estado/{estado}` | Buscar por estado |
| POST | `/api/cita` | Crear cita |
| PUT | `/api/cita/{id}` | Actualizar cita |
| PUT | `/api/cita/{id}/estado` | Cambiar estado |
| DELETE | `/api/cita/{id}` | Cancelar cita |

### Historias Clínicas
| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/historias-clinicas` | Listar historias |
| GET | `/api/historias-clinicas/{id}` | Buscar por ID |
| GET | `/api/historias-clinicas/pacientes/{id}` | Buscar por paciente |
| GET | `/api/historias-clinicas/doctores/{id}` | Buscar por doctor |
| POST | `/api/historias-clinicas` | Crear historia |
| PUT | `/api/historias-clinicas/{id}` | Actualizar historia |

### Recetas
| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/recetas` | Listar recetas |
| GET | `/api/recetas/{id}` | Buscar por ID |
| GET | `/api/recetas/pacientes/{id}` | Buscar por paciente |
| GET | `/api/recetas/historias-clinicas/{id}` | Buscar por historia clínica |
| POST | `/api/recetas` | Crear receta |

### Facturas
| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/facturas` | Listar facturas |
| GET | `/api/facturas/{id}` | Buscar por ID |
| GET | `/api/facturas/pacientes/{id}` | Buscar por paciente |
| GET | `/api/facturas/estado/{estado}` | Buscar por estado |
| POST | `/api/facturas` | Crear factura |
| PUT | `/api/facturas/{id}` | Actualizar factura |
| PUT | `/api/facturas/{id}/estado` | Cambiar estado |

---

## ⚙️ Configuración y Ejecución

### Prerequicitos
- Java 21+
- PostgreSQL instalado y en ejecución
- Maven

### 1. Crear la base de datos

```sql
CREATE DATABASE hospital_db;
```

### 2. Configurar credenciales

Editá `src/main/resources/application.properties` con tus datos de conexión:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hospital_db
spring.datasource.username=postgres
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
server.error.include-stacktrace=never
```

### 3. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

### 4. Verificar

- **API:** `http://localhost:8080/api/`
- **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI spec:** `http://localhost:8080/v3/api-docs`

---

## ✅ Validaciones (Bean Validation)

Todos los DTOs de entrada tienen validación declarativa con anotaciones de Jakarta Validation (`@NotBlank`, `@NotNull`, `@Size`, `@Pattern`, `@Email`, `@Future`, `@Min`, `@Max`, etc.). Si algún campo no cumple las reglas, la API retorna un 400 con los errores por campo:

```json
{
    "nombre": "El nombre no puede estar vacío",
    "email": "El formato del email no es válido"
}
```

---

## 📝 Pendientes

- [ ] Paginación con `Pageable` en endpoints de listado
- [ ] Spring Security + JWT
