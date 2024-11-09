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
| Workspace Service | 8087  | Manages workspaces                   |
| Elasticsearch    | 9200  | Elasticsearch                        |
| Logstash         | 5000  | Logstash                             |
| Kibana           | 5601  | Kibana                               |
| Zookeeper        | 2181  | Zookeeper                            |
| Kafka            | 9092  | Kafka                                |

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