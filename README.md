# 🎫 CRUD + KAFKA

This project is a crud users system built using **Quarkus**, **MySQL**, and **Kafka**.

---

## 🧱 Tech Stack

- Java 17
- Quarkus
- MySQL
- Kafka
- Flyway (for DB migrations)
- Docker + Docker Compose
- Swagger (for API documentation)

---

## 🚀 Getting Started

### 1. 📋 Prerequisites

Make sure you have the following installed:

- Java 17+
- Maven
- Docker + Docker Compose

### 2. 🗃️ Database Setup

- Create a user with `username` `root` and `password` `root`
- Create a database named `ifg_code_test` before running the application:

```sql
CREATE DATABASE ifg_code_test;
```

- Set user to be able to access database `ifg_code_test`
- or if you already have user, you can configure `application.properties` as follows:

```
quarkus.datasource.username=<your_username>
quarkus.datasource.password=<your_password>
```

### 3. 🐳 Running in Docker

- Build the Quarkus App
```
./mvnw package
```
- Build Docker Image
```
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/ifg-code-test-jvm .
```
- Start the Services
```
docker-compose up
```
- Setup kafka container in `docker-compose.yaml`
```
environment:
    # after change the host, delete the container first
    # KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka-container:9092
```

### 4. 💻 Running in Local

- Setup kafka container in `docker-compose.yaml`
```
environment:
    # after change the host, delete the container first
    # KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
```
- go to the project root, open terminal and run this command:
```
./mvnw quarkus:dev
```

### 5. 🔗 API Documentation
Once the app is running, you can check the available API endpoints here:
http://localhost:8080/swagger-ui/index.html

### 6. 🔧 How to use
- create a user
```
curl --location 'http://localhost:8080/webhook/user' \
--header 'Content-Type: application/json' \
--data '{
    "name": "name",
    "type": "add"
}'
```
- edit a user
```
curl --location 'http://localhost:8080/webhook/user' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "name": "name",
    "type": "edit"
}'
```
- delete a user
```
curl --location 'http://localhost:8080/webhook/user' \
--header 'Content-Type: application/json' \
--data '{
    "id":1,
    "type": "delete"
}'
```
- retry dead letter user
```
curl --location --request POST 'http://localhost:8080/webhook/retry-dead-letter-user'
```

### 7. ⚠️ Constraint and Limitation
- Authentication:
  This project does not implement login/token authentication to simplify testing under concurrent load. The focus is on demonstrating booking logic and quota control.
- Unit Tests:
  Unit tests are not included due to time constraints. Writing meaningful tests for such flows would require mocking Redis, DB, and possibly multi-thread scenarios — nearly as involved as
  building the actual logic.
