FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/busqueda-*.jar app.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "app.jar"]