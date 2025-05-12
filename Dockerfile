
# Folosește imaginea oficială OpenJDK
FROM openjdk:17-jdk-slim

# Instalare Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Setează directorul de lucru
WORKDIR /ecommerce-app

# Copiază fișierele sursă în container
COPY . /ecommerce-app

# Rulează Maven pentru build
RUN mvn clean install -DskipTests

# Expune portul pe care rulează aplicația
EXPOSE 8080

# Comanda de pornire a aplicației
ENTRYPOINT ["java", "-jar", "/ecommerce-app/target/ecommerce-app-0.0.1.jar"]
