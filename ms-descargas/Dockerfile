FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY */target/ms-descargas-*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]