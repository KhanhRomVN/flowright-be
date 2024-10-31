# API Gateway Service

The API Gateway service acts as the single entry point for all client requests in the Flowright microservices architecture. It handles routing, load balancing, and request forwarding to appropriate microservices.

## Features

- Centralized routing for microservices
- Load balancing using Spring Cloud Gateway
- Port: 8000

## Tech Stack

- Java 21
- Spring Boot 3.3.5
- Spring Cloud Gateway
- Maven 3.9.x
- Project Lombok

## Service Routes

Currently configured routes:

1. **Auth Service**
   - Path: `/api/auth/**`
   - Service ID: `AUTH-SERVICE`

2. **User Service**
   - Path: `/api/user/**`
   - Service ID: `USER-SERVICE`

## Prerequisites

- JDK 21
- Maven 3.9.x
- Docker (for containerization)

## Building the Application

### Using Maven
