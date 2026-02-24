Backend Banca API 
Este proyecto es una API REST para la gestión de cuentas bancarias y clientes, desarrollada con Java 17 y Spring Boot. La aplicación está completamente dockerizada para facilitar su despliegue y pruebas.

Tecnologías Utilizadas
Java 17 & Spring Boot 3.5.11

JPA / Hibernate (Persistencia)

PostgreSQL (Base de Datos)

Docker & Docker Compose

JUnit 5 & Mockito (Pruebas)

Lombok (Productividad)

Ejecución con Docker
El proyecto incluye un archivo docker-compose.yml que levanta tanto la aplicación como la base de datos de forma automática.
Bash
docker-compose up --build

Clonar el repositorio:

Bash
git clone https://github.com/AnthonyHolguin/backend-banca.git
cd backend_banca
Levantar el entorno:

Bash
docker-compose up --build
Nota: Al iniciar, la base de datos se creará automáticamente y se ejecutarán los scripts de src/main/resources/import.sql para cargar los datos iniciales necesarios (Clientes y Cuentas de prueba).

Pruebas (Testing)
Se han implementado dos niveles de pruebas para asegurar la calidad del código:

Pruebas Unitarias: Enfocadas en la lógica de negocio en la capa de Service utilizando Mockito para mockear los repositorios.

Pruebas de Integración: Verifican el flujo completo desde el Controller hasta la base de datos (MySQL) utilizando @SpringBootTest y @AutoConfigureMockMvc.



Recursos Adicionales
Postman Collection
He incluido la colección de Postman para probar todos los endpoints (CRUD de Clientes, Cuentas, y reportes).

Archivo: Banca_API.postman_collection.json (Ubicado en la raíz del proyecto).

Importación: Abre Postman > Import > Selecciona el archivo.

Desarrollado por: Anthony - Systems Engineer
