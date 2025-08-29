# PagerDuty Integration Platform

Este proyecto integra PagerDuty con una arquitectura moderna basada en Spring Boot, MySQL, Docker y React. Permite sincronizar servicios, equipos y políticas de escalamiento desde PagerDuty y visualizarlos en un dashboard profesional.

## Requisitos previos
- Docker y Docker Compose instalados
- Acceso a un token de API de PagerDuty

## Estructura del proyecto
```
├── backend (Spring Boot)
├── frontend (React)
├── docker-compose.yml
├── .env
```

## Configuración inicial
1. **Clona el repositorio**
2. **Configura las variables sensibles**
   - Edita el archivo `.env` en la raíz y coloca tu token de PagerDuty y credenciales de base de datos:
     ```
     PAGERDUTY_API_TOKEN=tu_token
     PAGERDUTY_API_URL=https://api.pagerduty.com
     SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/pagerduty?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
     SPRING_DATASOURCE_USERNAME=user
     SPRING_DATASOURCE_PASSWORD=password
     ```
   - **No subas el archivo `.env` al repositorio.**

## Arranque del proyecto
1. **Levanta todos los servicios con Docker Compose:**
   ```
   docker compose up -d --build
   ```
   Esto inicia:
   - Backend Spring Boot en el puerto 8080
   - Base de datos MySQL en el puerto 3306
   - Frontend React en el puerto 3000

2. **Accede al dashboard:**
   - Abre tu navegador en [http://localhost:3000](http://localhost:3000)
   - Verás el dashboard con menú lateral y las entidades sincronizadas.

## Funcionalidades principales
- **Sincronización:**
  - Botón "Sincronizar PagerDuty" para importar servicios, equipos y políticas desde la API de PagerDuty.
  - Los datos se actualizan automáticamente en el dashboard tras la sincronización.
- **Visualización:**
  - Tablas dinámicas para servicios, equipos y políticas.
  - Menú lateral para navegar entre entidades.
  - Estilo profesional basado en la paleta de colores de PagerDuty.
- **Backend:**
  - Endpoints REST para `/api/v1/services`, `/api/v1/teams`, `/api/v1/escalation-policies`.
  - Endpoint de sincronización: `/sync/pagerduty` (POST).
- **Frontend:**
  - React + Material UI + DataGrid para visualización moderna.
  - Proxy configurado para consumir el backend desde el frontend.

## Desarrollo y pruebas
- Para desarrollo local del frontend:
  ```
  cd frontend
  npm install
  npm start
  ```
- Para desarrollo local del backend:
  ```
  ./mvnw spring-boot:run
  ```
- Los tests se ejecutan con:
  ```
  ./mvnw test
  ```

## Seguridad
- Las credenciales y tokens deben ir en `.env` y nunca subirse al repositorio.
- El backend valida y maneja errores de la API de PagerDuty.

## Notas adicionales
- Si tienes problemas con dependencias en el frontend, asegúrate de que el archivo `.dockerignore` incluya `node_modules` y `build`.
- Puedes personalizar el dashboard modificando `frontend/src/App.js`.
- La base de datos se persiste en el volumen Docker `mysqldata`.

## Contacto y soporte
Para dudas, sugerencias o soporte, contacta al equipo de desarrollo o revisa la documentación oficial de PagerDuty.
