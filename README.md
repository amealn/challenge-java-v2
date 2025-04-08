# 🚀 Sale Points API (challenge-java-v2) 🚀

This service manages sale points, its costs and its credentials, providing secure access and data retrieval. Built with Spring Boot framework, it leverages Redis for caching and MongoDB for reliable persistent storage. Encapsulated in Docker containers for deployment and orchestrated by Kubernetes (using Kind for local development). Logs are collected by Fluent Bit, aggregated within Loki, and visualized through Grafana. Sensitive information is securely managed using Kubernetes Secrets.

## ✨ Technologies ✨

* **Core:** Java 17 ☕, Spring Boot 3.4.4 🚀
* **Data:** Redis (Caching) ⚡, MongoDB (Persistence) 💾
* **Build:** Maven 🛠️
* **Containerization:** Docker 🐳
* **Orchestration:** Kubernetes (Kind) ☸️
* **Logging:** Fluent Bit (Forwarding), Loki (Aggregation), Grafana (Visualization) 📊

## 🛠️ Getting Started 🛠️

### ⚙️ Pre-requisites

Ensure the following are installed on your system:

* [Java Development Kit (JDK) 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) (from Oracle) ☕
* Alternatively, you can find OpenJDK 17 distributions [here](https://openjdk.java.net/install/) or via your operating system's package manager.
* [Maven 3.8.0+](https://maven.apache.org/install.html) 🛠️
* [Docker](https://docs.docker.com/get-docker/) 🐳
* **Kind** (for local Kubernetes cluster) ☸️ - [Installation Guide](https://kind.sigs.k8s.io/docs/user/quick-start#installation)
* **kubectl** (Kubernetes command-line tool) ☸️ - [Installation Guide](https://kubernetes.io/docs/tasks/tools/)

### 💾 Cloning the Repository

```bash
git clone https://github.com/amealn/challenge-java-v2.git
cd challenge-java-v2
```

### 🔑 Adding `secrets.yaml`

Locate the `secrets.yaml` file provided by the administrator and place it in the `k8s` directory of your cloned repository. This file contains sensitive configuration as Kubernetes Secrets.

### 🏃 Running Locally (with Kind)

Follow these steps to build and deploy the application on your local Kind Kubernetes cluster:

```bash
mvn clean deploy
kind create cluster --name kind
kind load docker-image challenge-java-v2:latest --name kind
kubectl apply -f k8s/
```

### 🌐 Accessing Services

Open two separate terminal windows to set up port forwarding for accessing the API and Grafana:

### 🔗 Swagger UI (API Documentation) 📖

```bash
kubectl port-forward service/app-service 8080:2911
```

Open your web browser and navigate to: http://localhost:8080/swagger-ui.html to explore the API documentation.

### 📊 Grafana (Log Visualization) 📈

```bash
kubectl port-forward service/grafana 3000:3000
```

Open your web browser and navigate to: [http://localhost:3000](http://localhost:3000)

**Login Credentials:**

- **Username:** `admin` 👤
- **Password:** `admin` 🔑

**Add Loki Data Source:**

1. Go to **Connections** (or **Data Sources**).
2. Click **Add new connection**.
3. Search for and select **Loki**.
4. Enter the URL: `http://loki.default.svc.cluster.local:3100`
5. Click **Save & test**.

**View Application Logs:**

1. Navigate to the **Explore** section.
2. Select your **Loki** data source.
3. Run the following LogQL query to see your application logs:

```bash
{job="challenge-java-v2", container="app"}
```

