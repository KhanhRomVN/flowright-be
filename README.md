<div align="center">

# 🌟 FloWright Backend Services - Task Management System

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Email](https://img.shields.io/badge/Email-khanhromvn%40gmail.com-blue.svg)](mailto:khanhromvn@gmail.com)

*A powerful microservices-based project management system built with Spring Boot*

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
- 🔐 JWT-based Authentication & Authorization
- 👥 User & Team Management
- 🏢 Workspace Organization
- 📊 Project Tracking
- ✅ Task Management
- 📨 Email Notifications
- 🔍 Advanced Search Capabilities

## 💻 Tech Stack

- **Framework:** Spring Boot 3.2.5
- **Language:** Java 21
- **Security:** JWT, Spring Security
- **Database:** MySQL
- **Documentation:** SpringDoc OpenAPI
- **Build Tool:** Maven
- **Cloud:** Spring Cloud
- **Testing:** JUnit 5, Mockito

## 🌐 Services Overview

| Service | Port | Description |
|---------|------|-------------|
| API Gateway | 8080 | Routes and filters requests |
| Auth Service | 8081 | Handles authentication & authorization |
| Member Service | 8082 | Manages workspace membership |
| Project Service | 8083 | Handles project management |
| Task Service | 8084 | Manages tasks and assignments |
| Team Service | 8085 | Handles team organization |
| User Service | 8086 | Manages user profiles |
| Workspace Service | 8087 | Manages workspaces |

## 🚀 Getting Started

### Prerequisites
- Java 21
- Maven
- MySQL Cloud Aiven
- Docker (optional)

### Installation

1. Clone the repository
```
git clone https://github.com/KhanhRomVN/flowright-be.git
cd flowright-be
```

2. Build all services
```
[1]. cd api_gateway
[2]. mvn clean install
[3]. cd ..
[4]. cd auth_service
[5]. mvn clean install
[6]. cd ..
[7]. cd member_service
[8]. mvn clean install
[9]. cd ..
[10]. cd project_service
[11]. mvn clean install
[12]. cd ..
[13]. cd task_service
[14]. mvn clean install
[15]. cd ..
[16]. cd team_service
[17]. mvn clean install
[18]. cd ..
[19]. cd user_service
[20]. mvn clean install
[21]. cd ..
[22]. cd workspace_service
[23]. mvn clean install
```

3. Start the services
[1]. Run Kafka Docker Compose
```
docker-compose up -d
```
[2]. Run All Services
- if you use vscode ide
```
mvn spring-boot:run
```
- if you run intelji ide, click the green play button on the left sidebar


## 📚 API Documentation

Access the API documentation at:
- Gateway Swagger UI: `http://localhost:8080/swagger-ui.html`
- Individual service documentation:
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

member-serivce
[1].Member Management
*Create Member: Registers a new member within a workspace and assigns a default role.*
*Update Member: Allows updating a member’s role.*
*Delete Member: Removes a member by ID.*
*Get Member by ID: Retrieves member details by member ID.*
*Get Members by Workspace: Lists all members within a specific workspace.*
*Create First Member: Adds the initial member to a workspace, typically an admin or owner.*

[2].Role Management
*Create Role: Allows creation of a role with a name, description, and workspace association.*
*Update Role: Updates role details by ID.*
*Delete Role: Deletes a role by ID.*
*Get Role by ID: Retrieves a role by its ID.*
*Get All Roles: Lists roles within a workspace.*
*Get Admin Role: Fetches the admin role for a workspace.*

[3].Permission Management
*Create Permission: Adds a new permission for workspace actions.*
*Delete Permission: Removes permission by ID.*
*Get All Permissions: Lists permissions with pagination.*

[4].Role-Permission Assignment
*Assign Permission to Role: Links a permission to a role.*
*Remove Permission from Role: Removes a permission from a role.*
*Get Role Permissions: Lists permissions assigned to a specific role.*

[5].Specialization Management
*Create Specialization: Adds a new specialization associated with a workspace.*
*Delete Specialization: Removes specialization by ID.*

[6].Member Specialization Management
*Add Specialization to Member: Links a specialization to a member, including level and years of experience.*


