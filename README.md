#Sale Points API (challenge-java-v2)

This service manages sale points, its costs and its credentials, providing secure access and data retrieval. It leverages Spring Boot, Redis for caching, and MongoDB for persistent storage, all containerized with Docker for easy deployment.

## Technologies

* Java 17
* Spring Boot 3.4.4
* Redis (for caching)
* MongoDB (for data storage)
* Docker & Docker Compose
* Maven

## Getting Started

### Prerequisites

* Java Development Kit (JDK) 17 or later
* Maven 3.8.0 or later
* Docker and Docker Compose (recommended)

### Cloning the Repository

git clone https://github.com/amealn/challenge-java-v2.git
cd challenge-java-v2

### .env file

Create an .env file with credentials provided by the administrator
An .env.test file is provided as an example of the contents

## Profiles

* **`prod`:** Uses MongoDB Atlas (configured via `MONGODB_ATLAS_URI`).
* **`test`:** Uses a Dockerized MongoDB for isolated integration testing.

Profiles are set via `SPRING_PROFILES_ACTIVE`. Docker Compose handles this automatically; Maven requires manual setting.

## Docker Setup

* **`Dockerfile`:** Builds the application image for production.
* **`Dockerfile.test`:** Builds a test image, including test dependencies.
* **`docker-compose.yml`:** Starts the application and Redis for production.
* **`docker-compose.test.yml`:** Starts the application, Redis, and MongoDB for testing.
* **`init.mongo.js`:** Initializes the MongoDB test database.

## Running the Application

Use these Maven commands with Docker Compose:

1.  **Production:**

    ```bash
    mvn clean package docker:build docker-compose:up -P prod
    ```

    Base directory: `${workspace_loc:/challenge-java-v2}`

2.  **Testing:**

    ```bash
    mvn clean verify docker-compose:up -P test
    ```

    Base directory: `${workspace_loc:/challenge-java-v2}`

**Configuration:**

* `.env` file is required for `prod` profile (`MONGODB_ATLAS_URI`).
* Docker Compose files must be correctly configured for both profiles.