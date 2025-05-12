# Sistema de Gestión de Empleados

Este proyecto es una implementación de un sistema de gestión de empleados utilizando Clean Architecture en Java con Spring Boot.

## 🏗️ Estructura del Proyecto

El proyecto está organizado siguiendo los principios de Clean Architecture, dividido en las siguientes capas:

```
├── domain/           # Capa de dominio - Contiene las entidades y reglas de negocio
├── application/      # Capa de aplicación - Casos de uso y lógica de aplicación
├── infrastructure/   # Capa de infraestructura - Implementaciones técnicas
```

### Capas

- **Domain**: Contiene las entidades de negocio, interfaces de repositorio y reglas de negocio.
- **Application**: Implementa los casos de uso y la lógica de aplicación.
- **Infrastructure**: Contiene las implementaciones concretas de las interfaces definidas en el dominio.

## 🚀 Tecnologías Utilizadas

- Java 21
- Spring Boot
- Gradle
- JPA/Hibernate
- H2
- Lombok
- JUnit 5

## 📋 Prerrequisitos

- JDK 21 o superior
- Gradle 7.x o superior

## 🔧 Configuración

1. Clonar el repositorio:
```bash
git clone git@github.com:c-aragon/employeeservice.git
```

2. Configurar la base de datos:
   - Configurar las credenciales en `application.properties`

3. Ejecutar el proyecto:
```bash
./gradlew bootRun
```

## 🏗️ Estructura de la Base de Datos

### Tabla Employee
- id (PK)
- first_name
- middle_name
- last_name
- mothers_last_name
- age
- gender
- birth_date
- position

## 📝 API Endpoints

### Empleados
- `GET /api/v1/employees` - Listar todos los empleados

```bash
   curl --location 'http://localhost:8080/api/v1/employees?page=0&size=10&sortBy=id&direction=asc' \
--header 'Accept: */*'
```

- `POST /api/v1/employees` - Crear nuevos empleados (Uno o varios)
  
```bash
curl --location 'http://localhost:8080/api/v1/employees' \
  --header 'Content-Type: application/json' \
  --header 'Accept: */*' \
  --data '[
  {
  "firstName": "CARLOS",
  "middleName": "AUGUSTO",
  "lastName": "ARAGÓN",
  "mothersLastName": "CRUZ",
  "gender": "MAN",
  "birthDate": "03-04-1990",
  "position": "PROJECT_MANAGER"
  }
  ]'
```

- `PUT /api/v1/employees/{id}` - Actualizar empleado

```bash
curl --location --request PUT 'http://localhost:8080/api/v1/employees/<long>' \
--header 'Content-Type: application/json' \
--header 'Accept: */*' \
--data '{
  "firstName": "Carlos",
  "middleName": "Augusto",
  "lastName": "Aragón",
  "mothersLastName": "Cruz",
  "birthDate": "28-11-1985",
  "gender": "MAN",
  "position": "DEVELOPER"
}'
```

- `DELETE /api/v1/employees/{id}` - Eliminar empleado

```bash
curl --location --request DELETE 'http://localhost:8080/api/v1/employees/<long>'
```

## 🧪 Testing & coverage

Para ejecutar las pruebas:
```bash
./gradlew test jacocoTestReport
```

## 📦 Construcción

Para construir el proyecto:
```bash
./gradlew build
```

## 🏃 Ejecución con docker

Para ejecutar el proyecto en una máquina con docker (la imagen es pública):
```bash
docker run -d -p8080:8080 ingaragon/employeeservice
```

Para ingresar a la documentación (Una vez iniciado el proyecto con el comando anterior)

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)


## 🤝 Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## ✨ Características

- Implementación de Clean Architecture
- Gestión completa de empleados
- Validación de datos
- Manejo de errores
- Documentación de API
- Tests unitarios y de integración

## 📞 Contacto

[Carlos Aragón] - [ing.c.aragon@gmail.com]

Link del proyecto: [https://github.com/c-aragon/employeeservice.git](https://github.com/c-aragon/employeeservice.git) 