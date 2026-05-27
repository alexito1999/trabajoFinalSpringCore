# Trabajo Final — Spring Boot + Thymeleaf

Aplicación web de gestión de preguntas tipo test con juego interactivo.

## Credenciales de demo

| Usuario | Contraseña | Rol |
|---------|-----------|-----|
| `admin` | `admin123` | ADMIN — puede crear/editar/borrar preguntas |
| `user` | `user123` | USER — solo puede jugar |

## Cómo levantar la aplicación

### Opción 1 — Docker (recomendado, no necesita Java ni Maven)

Requiere [Docker](https://docs.docker.com/engine/install/) instalado.

**Perfil dev** (H2 en memoria, sin base de datos externa):
```bash copiar y pegar el comando aparecera una marca de agua indicando que estas en desarrollo
docker compose --profile dev up
```

**Perfil prod** (con MySQL):
```bash copiar y pegar el comando
docker compose --profile prod up
```

En ambos casos la app estará en `http://localhost:8080`.

### Opción 2 — Maven (desarrollo local)

Requiere JDK 17+ y Maven (o usar `./mvnw`).

```bash
./mvnw spring-boot:run
```

Por defecto arranca con el perfil `dev` (H2 en memoria).

## Perfiles

| Perfil | Base de datos | Archivo de datos |
|--------|--------------|------------------|
| `dev` (por defecto) | H2 en memoria | `data-h2.sql` |
| `prod` | MySQL | `data-mysql.sql` |

Para cambiar de perfil con Maven:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

## Estructura del proyecto

- `src/main/java/.../controller/` — Controladores MVC y REST
- `src/main/java/.../models/` — Entidades JPA
- `src/main/java/.../service/` — Lógica de negocio
- `src/main/resources/templates/` — Vistas Thymeleaf
- `src/main/resources/static/` — CSS, JS, imágenes
- `src/main/resources/data-h2.sql` / `data-mysql.sql` — Datos de demo
