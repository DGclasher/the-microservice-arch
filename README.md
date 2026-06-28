# Microservice Architecture with Spring Boot

This project demonstrates a Spring Boot microservice architecture using Spring Cloud, Eureka service discovery, API Gateway routing, Kafka messaging, database-backed services, distributed tracing, metrics, dashboards, and Docker-based local infrastructure.

## Overview

Services included:

- `product-service` - Manages product data in MongoDB.
- `inventory-service` - Tracks stock availability in MySQL.
- `order-service` - Places orders, checks inventory, and publishes order events to Kafka.
- `notification-service` - Consumes order events from Kafka and logs order notifications.
- `api-gateway` - Single entry point for client requests.
- `discovery-server` - Eureka service registry.
- `prometheus` - Scrapes service metrics.
- `grafana` - Visualizes metrics using dashboards.

The services run independently and communicate through service discovery, HTTP calls, gateway routing, and Kafka events.

## Local Infrastructure

The main Docker Compose file is available in the `composes` directory:

- `composes/application.yml` - Starts the full application stack, including MySQL, MongoDB, Kafka, Keycloak, Zipkin, Prometheus, Grafana, Eureka, API Gateway, and the application services.

Build the service Docker images from the project root:

```bash
mvn clean compile jib:dockerBuild
```

Then start the stack from the `composes` directory:

```bash
cd composes
docker compose -f application.yml up -d
```

Stop the stack from the `composes` directory:

```bash
docker compose -f application.yml down
```

To stop and remove the persisted volumes as well:

```bash
docker compose -f application.yml down -v
```

## Infrastructure Defaults

MySQL:

- Host: `localhost`
- Port: `3306`
- Database: `db`
- Username: `user`
- Password: `password`

MongoDB:

- Host: `localhost`
- Port: `27017`
- Database: `dbmongo`
- Username: `mongo`
- Password: `password`
- Authentication database: `admin`

Kafka:

- Bootstrap server: `localhost:9092`
- Notification topic: `notificationTopic`

Prometheus:

- URL: `http://localhost:9090`

Grafana:

- URL: `http://localhost:3000`
- Username: `admin`
- Password: `admin`

## Keycloak

Keycloak admin console:

```text
http://localhost:8181
```

Default admin credentials:

- Username: `admin`
- Password: `admin`

### Realm Setup

Create a Keycloak realm named:

```text
springboot-microservices-realm
```

The API Gateway expects JWT tokens from this realm.

## Zipkin

The services are configured to export traces to Zipkin at:

```text
http://localhost:9411/api/v2/spans
```

Zipkin UI:

```text
http://localhost:9411
```

If Zipkin is not running, the services can still run, but you may see connection errors from the async Zipkin reporter.

## Metrics and Dashboards

Prometheus is configured in `composes/application.yml` and uses:

```text
composes/prometheus/prometheus.yml
```

Grafana is available at:

```text
http://localhost:3000
```

Import the Grafana dashboard from:

```text
./files/grafana_dashboard.json
```

## Service Notes

- `product-service` uses MongoDB.
- `inventory-service` uses MySQL.
- `order-service` uses MySQL and publishes Kafka events.
- `notification-service` consumes Kafka events and logs order notifications.
- `api-gateway` routes traffic to backend services and integrates with Keycloak.
- `discovery-server` runs Eureka and should be available at `localhost:8761`.
