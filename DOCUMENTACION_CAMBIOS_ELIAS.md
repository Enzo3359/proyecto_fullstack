# Documentación de Cambios - Microservicios Elías

## Proyecto

**Nombre del proyecto:** DurocPlus
**Repositorio:** proyecto_fullstack
**Rama utilizada:** microservicios-elias
**Responsable:** Elías
**Fecha de cambios:** 07-06-2026

## Objetivo de los cambios

Se realizaron correcciones y mejoras en los microservicios asignados para cumplir con los requerimientos solicitados:

* Integración entre microservicios.
* Incorporación de autenticación con Spring Security.
* Corrección de errores menores y funcionales.
* Revisión de estructura del proyecto.
* Validación de compilación mediante Maven.

---

# Microservicio: Suscripciones

## Cambios realizados

### 1. Integración entre microservicios

Se revisó y corrigió la integración del microservicio `suscripciones` con el microservicio `RegistroUsuario`.

Se utilizó `FeignClient` mediante la clase `UsuarioClient`, apuntando al endpoint:

```
GET /api/v1/auth/usuarios/{id}
```

Esto permite que antes de crear una suscripción se consulte si el usuario existe.

### 2. Modificación en `UsuarioClient`

Se corrigió el uso de `@PathVariable("id")`, dejando explícito el parámetro `id`.

Esto mejora la comunicación entre el endpoint y el cliente Feign.

### 3. Modificación en `SuscripcionService`

Se incorporó el uso de `UsuarioClient` dentro del método de creación de suscripción.

Ahora el flujo es:

```
1. Recibir datos de la suscripción.
2. Consultar al microservicio RegistroUsuario si el usuario existe.
3. Si el usuario existe, crear la suscripción.
4. Si el usuario no existe, devolver error.
```

### 4. Incorporación de Spring Security

Se agregó la dependencia de Spring Security en el archivo `pom.xml`:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### 5. Creación de `SecurityConfig`

Se creó la clase:

```
com.durocplus.suscripciones.config.SecurityConfig
```

Esta configuración protege los endpoints de suscripciones usando autenticación Basic Auth.

### 6. Configuración en `application.properties`

Se agregaron configuraciones para Docker, integración entre microservicios y autenticación:

```
ms.usuario.url=http://host.docker.internal:8082
spring.security.user.name=admin
spring.security.user.password=admin123
```

### 7. Compilación

El microservicio `suscripciones` fue compilado correctamente mediante Maven en IntelliJ.

Resultado:

```
BUILD SUCCESS
```

---

# Microservicio: Búsqueda

## Cambios realizados

### 1. Integración con RegistroUsuario

Se revisó el cliente `AuthClient`, encargado de comunicarse con el microservicio `RegistroUsuario`.

El endpoint utilizado es:

```
GET /api/v1/auth/usuarios/{id}
```

Se corrigió el uso de `@PathVariable("id")` para evitar errores en la comunicación con Feign.

### 2. Integración con Catálogo

Se revisó el cliente `BusquedaClient`, encargado de consultar contenidos desde el microservicio `Catálogo`.

Antes la URL estaba escrita directamente en el código:

```
http://localhost:8081
```

Esto fue corregido para usar una variable desde `application.properties`:

```
ms.catalogo.url=http://host.docker.internal:8081
```

De esta forma, el microservicio queda mejor preparado para ejecutarse con Docker.

### 3. Modificación en `BusquedaClient`

Se modificó `BusquedaClient` para recibir la URL del microservicio Catálogo mediante la propiedad:

```
ms.catalogo.url
```

Esto evita dejar rutas fijas dentro del código fuente.

### 4. Incorporación de Spring Security

Se agregó la dependencia de Spring Security en el archivo `pom.xml`:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### 5. Creación de `SecurityConfig`

Se creó la clase:

```
com.busqueda.busqueda.config.SecurityConfig
```

Esta configuración protege los endpoints del microservicio búsqueda usando autenticación Basic Auth.

### 6. Configuración en `application.properties`

Se agregaron y corrigieron configuraciones para Docker, seguridad e integración:

```
ms.auth.url=http://host.docker.internal:8082
ms.catalogo.url=http://host.docker.internal:8081
spring.security.user.name=admin
spring.security.user.password=admin123
```

También se corrigió la ruta de Flyway:

```
spring.flyway.locations=classpath:db.migration
```

### 7. Compilación

El microservicio `busqueda` fue compilado correctamente mediante Maven en IntelliJ.

Resultado:

```
BUILD SUCCESS
```

---

# Registro en Git

Los cambios fueron registrados mediante Git en la rama:

```
microservicios-elias
```

Commit realizado:

```
Agrego security e integracion entre microservicios
```

El commit fue subido correctamente al repositorio remoto:

```
origin/microservicios-elias
```

---

# Resumen final

Se corrigieron y mejoraron los microservicios `suscripciones` y `busqueda`, agregando integración entre microservicios, autenticación con Spring Security, configuración para Docker y validación de compilación exitosa.

Ambos microservicios quedaron compilando correctamente y los cambios fueron registrados en GitHub.
