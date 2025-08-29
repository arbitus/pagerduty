<<<<<<< HEAD
# Dockerfile para React frontend
FROM node:20-alpine
WORKDIR /app
COPY package.json package-lock.json ./
COPY src ./src
COPY public ./public
RUN npm install --legacy-peer-deps
EXPOSE 3000
CMD ["npm", "start"]
=======
# Dockerfile para la aplicación Spring Boot PagerDuty
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/pagerduty-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
>>>>>>> efd14ea7 (Resubmitting the project)
