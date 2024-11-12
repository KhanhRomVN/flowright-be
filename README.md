<div align="center">

# 🌟 FloWright Backend Services - Task Management System

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Email](https://img.shields.io/badge/Email-khanhromvn%40gmail.com-blue.svg)](mailto:khanhromvn@gmail.com)

*✨ A powerful microservices-based project management system built with Spring Boot*

[View Demo](https://github.com/KhanhRomVN/flowright-be) · 
[Report Bug](https://github.com/KhanhRomVN/flowright-be/issues) · 
[Request Feature](https://github.com/KhanhRomVN/flowright-be/issues)

</div>

---

## 📋 Table of Contents
- [About The Project](#-about-the-project)
- [System Architecture](#-system-architecture)
- [Tech Stack](#-tech-stack)
- [Services Overview](#-services-overview)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Contributing](#-contributing)
- [License](#-license)
- [Contact](#-contact)

## 🚀 About The Project

FloWright is a comprehensive project management system built using microservices architecture. It provides robust features for team collaboration, project tracking, and task management with a focus on scalability and maintainability.

### Key Features
- 🔐 **JWT-based Authentication & Authorization**
- 👥 **User & Team Management**
- 🏢 **Workspace Organization**
- 📊 **Project Tracking**
- ✅ **Task Management**
- 📨 **Email Notifications**
- 🔍 **Advanced Search Capabilities**

## 💻 Tech Stack

- **Framework:** Spring Boot 3.2.5
- **Language:** Java 21
- **Security:** JWT, Spring Security
- **Database:** MySQL
- **Documentation:** SpringDoc OpenAPI
- **Build Tool:** Maven
- **Cloud:** Spring Cloud
- **Testing:** JUnit 5, Mockito
- **Logging:** ELK Stack (Elasticsearch, Logstash, Kibana)
- **Messaging:** Kafka, Zookeeper
- **Caching:** Redis

## 🌐 Services Overview

| Service          | Port  | Description                          |
|------------------|-------|--------------------------------------|
| API Gateway      | 8080  | Routes and filters requests          |
| Auth Service     | 8081  | Handles authentication & authorization|
| Member Service   | 8082  | Manages workspace membership         |
| Project Service  | 8083  | Handles project management           |
| Task Service     | 8084  | Manages tasks and assignments        |
| Team Service     | 8085  | Handles team organization            |
| User Service     | 8086  | Manages user profiles                |
| Workspace Service| 8087  | Manages workspaces                   |
| Elasticsearch    | 9200  | Elasticsearch                        |
| Logstash         | 5000  | Logstash                             |
| Kibana           | 5601  | Kibana                               |
| Zookeeper        | 2181  | Zookeeper                            |
| Kafka            | 9092  | Kafka                                |
| Redis            | 6379  | Redis                                |
| Grafana          | 3000  | Grafana                              |
| Prometheus       | 9090  | Prometheus                           |

## 🚀 Getting Started

### Prerequisites
- Java 21 SDK
- Maven
- Vscode or IntelliJ IDE
- Docker Desktop

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/KhanhRomVN/flowright-be.git
   cd flowright-be
   ```

2. **Build ELK, Kafka, Zookeeper, Redis using Docker Compose**
   ```bash
   docker-compose up -d
   ```

3. **Build all Spring Boot services**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Run all Spring Boot services**
   ```bash
   mvn spring-boot:run
   ```

5. **Run Docker Compose for Kafka, Zookeeper, Redis by opening Docker Desktop and starting it.**

## Grafana & Prometheus Tutorial
1. Access Grafana at `http://localhost:3000`
2. Access Prometheus at `http://localhost:9090`
3. Go to `Configuration` -> `Data Sources` -> `Add data source` -> Select `Prometheus` -> `Name`: `Prometheus` -> `Url`: `http://localhost:9090` -> `Save & Test`
4. Go to `Dashboards` -> `New` -> `Import` -> `Upload .json file` -> Import Dashboard

## Kubernetes Tutorial (Optional)
1. To run all services in Kubernetes, you can use the `k8s` folder.
2. Make sure you have minikube installed.
3. Run `minikube start` to start the cluster.
4. Run `kubectl apply -f k8s` to deploy all services.

Make sure:
- you have Docker Desktop installed.
- you have minikube installed.
- you have kubectl installed.
- you have minikube installed.
- you have deloy image in Docker Hub.

## Deploy Image to Docker Hub Tutorial
1. Turn on Docker Desktop
2. docker login
3. Run `docker build -t <your-dockerhub-username>/<service-name>:<tag> .` for each service.
4. Run `docker push <your-dockerhub-username>/<service-name>:<tag>` for each service.

Example:
If you want to deploy auth_service image to Docker Hub, make sure you are in the root folder and mvn clean install then start all services docker compose.
```bash
docker build -t khanhromvn/auth_service:latest .
docker push khanhromvn/auth_service:latest
```
api_gateway
```bash
docker build -t khanhromvn/flowright_api_gateway:latest .
docker push khanhromvn/flowright_api_gateway:latest
```
auth_service
```bash
docker build -t khanhromvn/flowright_auth_service:latest .
docker push khanhromvn/flowright_auth_service:latest
```
member_service
```bash
docker build -t khanhromvn/flowright_member_service:latest .
docker push khanhromvn/flowright_member_service:latest
```
project_service
```bash
docker build -t khanhromvn/flowright_project_service:latest .
docker push khanhromvn/flowright_project_service:latest
```
task_service
```bash
docker build -t khanhromvn/flowright_task_service:latest .
docker push khanhromvn/flowright_task_service:latest
```
team_service
```bash
docker build -t khanhromvn/flowright_team_service:latest .
docker push khanhromvn/flowright_team_service:latest
```
user_service
```bash
docker build -t khanhromvn/flowright_user_service:latest .
docker push khanhromvn/flowright_user_service:latest
```
workspace_service
```bash
docker build -t khanhromvn/flowright_workspace_service:latest .
docker push khanhromvn/flowright_workspace_service:latest
```

Run `kubectl apply -f k8s` to deploy all services.
Run `minikube dashboard` to view the dashboard.

## 📚 API Documentation

Access the API documentation at:
- **Gateway Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **Individual service documentation:**
  - Auth Service: `http://localhost:8081/swagger-ui.html`
  - Member Service: `http://localhost:8082/swagger-ui.html`
  - Project Service: `http://localhost:8083/swagger-ui.html`
  - Task Service: `http://localhost:8084/swagger-ui.html`
  - Team Service: `http://localhost:8085/swagger-ui.html`
  - User Service: `http://localhost:8086/swagger-ui.html`
  - Workspace Service: `http://localhost:8087/swagger-ui.html`

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the Project
2. Create your Feature Branch (`git checkout -b <branch-name>`)
3. Commit your Changes (`git commit -m '<commit-message>'`)
4. Push to the Branch (`git push origin <branch-name>`)
5. Open a Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

## 📫 Contact

KhanhRom - [@KhanhRomVN](https://github.com/KhanhRomVN) - khanhromvn@gmail.com

Project Link: [https://github.com/KhanhRomVN/flowright-be](https://github.com/KhanhRomVN/flowright-be)

---

<div align="center">

### ⭐ Star us on GitHub — it motivates us a lot!

</div>