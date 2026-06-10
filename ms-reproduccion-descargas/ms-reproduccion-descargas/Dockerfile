FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY */target/ms-reproduccion-*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]