FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/quarkus-app/ /app/

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "/app/quarkus-run.jar"]
