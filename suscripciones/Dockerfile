FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY */target/suscripciones-*.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]