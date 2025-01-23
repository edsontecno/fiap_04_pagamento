FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/microservice-payment.jar .

ENV DATABASE_URL=URL
ENV MERCADOPAGO_TOKEN=ABC

EXPOSE 8080

CMD ["java", "-jar", "-Ddatabase_url=${DATABASE_URL}", "-Dmercadopago_token=${MERCADOPAGO_TOKEN}", "microservice-payment.jar"]