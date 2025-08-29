# Dockerfile para la aplicación Spring Boot PagerDuty
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
# Copia el wrapper de Maven y los archivos fuente
COPY . .
# Compila el proyecto
RUN chmod +x ./mvnw
RUN ./mvnw clean package

# Segunda etapa: solo el JRE y el JAR generado
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/pagerduty-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
