# Stage 1: Build the application
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
RUN chmod +x gradlew
RUN ./gradlew dependencies || true
COPY src ./src
RUN ./gradlew bootJar

# Stage 2: Create the final image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# Environment variables
ENV SPRING_PROFILES_ACTIVE=local
ENV TNS_ADMIN=/wallet

EXPOSE 8080
# Set oracle.net.tns_admin as JVM system property for Oracle JDBC driver
ENTRYPOINT ["java", "-Doracle.net.tns_admin=/wallet", "-jar", "app.jar"]
