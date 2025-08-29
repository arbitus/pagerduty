
# PagerDuty Integration Platform

This project integrates PagerDuty with a modern architecture based on Spring Boot, MySQL, Docker, and React. It allows you to synchronize services, teams, and escalation policies from PagerDuty and visualize them in a professional dashboard.

## Prerequisites
- Docker and Docker Compose installed
- Access to a PagerDuty API token

## Project Structure
```
├── backend (Spring Boot)
├── frontend (React)
├── docker-compose.yml
├── .env (must be created manually)
```

## Initial Setup
1. **Clone the repository**
2. **Configure sensitive variables**
   - Create the `.env` file in the root directory and add your PagerDuty token and database credentials:
     ```
     PAGERDUTY_API_TOKEN=your_token
     PAGERDUTY_API_URL=https://api.pagerduty.com
     SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/pagerduty?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
     SPRING_DATASOURCE_USERNAME=user
     SPRING_DATASOURCE_PASSWORD=password
     ```
   - **Do not commit the `.env` file to the repository.**

## Starting the Project
1. **Start all services with Docker Compose:**
   ```
   docker compose up -d --build
   ```
   This will start:
   - Spring Boot backend on port 8080
   - MySQL database on port 3306
   - React frontend on port 3000

2. **Access the dashboard:**
   - Open your browser at [http://localhost:3000](http://localhost:3000)
   - You will see the dashboard with a sidebar menu and synchronized entities.

## Main Features
- **Synchronization:**
  - "Synchronize PagerDuty" button to import services, teams, and policies from the PagerDuty API.
  - Data is automatically updated in the dashboard after synchronization.
- **Visualization:**
  - Dynamic tables for services, teams, and policies.
  - Sidebar menu to navigate between entities.
  - Professional style based on PagerDuty's color palette.
- **Backend:**
  - REST endpoints for `/api/v1/services`, `/api/v1/teams`, `/api/v1/escalation-policies`.
  - Synchronization endpoint: `/sync/pagerduty` (POST).
- **Frontend:**
  - React + Material UI + DataGrid for modern visualization.
  - Proxy configured to consume the backend from the frontend.

## Development and Testing
- For local frontend development:
  ```
  cd frontend
  npm install
  npm start
  ```
- For local backend development:
  ```
  ./mvnw spring-boot:run
  ```
- Run tests with:
  ```
  ./mvnw test
  ```

## Security
- Credentials and tokens must be placed in `.env` and never committed to the repository.
- The backend validates and handles errors from the PagerDuty API.

## Additional Notes
- If you have issues with frontend dependencies, make sure `.dockerignore` includes `node_modules` and `build`.
- You can customize the dashboard by editing `frontend/src/App.js`.
- The database is persisted in the Docker volume `mysqldata`.

## Contact & Support
For questions, suggestions, or support, contact the development team or refer to the official PagerDuty documentation.
