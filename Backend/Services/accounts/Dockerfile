# Stage 1: Build the application
FROM openjdk:21 AS build
WORKDIR /workspace
COPY . .
RUN ./mvnw install

# Stage 2: Run the application
FROM openjdk:21
WORKDIR /app
COPY --from=build /workspace/target/accounts.jar /app/accounts.jar
CMD ["java", "-jar", "/app/accounts.jar"]
