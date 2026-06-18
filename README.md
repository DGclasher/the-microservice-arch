# Microservice Architecture with Spring Boot

This project demonstrates a Spring Boot microservice architecture using Spring Cloud, Eureka service discovery, API Gateway routing, Kafka messaging, database-backed services, distributed tracing, and Docker-based local infrastructure.

## Overview

Services included:

- `product-service` - Manages product data in MongoDB.
- `inventory-service` - Tracks stock availability in MySQL.
- `order-service` - Places orders, checks inventory, and publishes order events to Kafka.
- `notification-service` - Consumes order events from Kafka and logs order notifications.
- `api-gateway` - Single entry point for client requests.
- `discovery-server` - Eureka service registry.

The services run independently and communicate through service discovery, HTTP calls, gateway routing, and Kafka events.

## Local Infrastructure

Docker Compose files are available in the `composes` directory:

- `composes/mysql.yml` - Starts MySQL on port `3306`.
- `composes/mongo.yml` - Starts MongoDB on port `27017`.
- `composes/kafka.yml` - Starts Kafka on port `9092`.

Start each dependency from the project root:

```bash
docker compose -f composes/mysql.yml up -d
docker compose -f composes/mongo.yml up -d
docker compose -f composes/kafka.yml up -d
```

Stop them when finished:

```bash
docker compose -f composes/mysql.yml down
docker compose -f composes/mongo.yml down
docker compose -f composes/kafka.yml down
```

To stop and remove the persisted volumes as well:

```bash
docker compose -f composes/mysql.yml down -v
docker compose -f composes/mongo.yml down -v
docker compose -f composes/kafka.yml down -v
```

## Run with Docker Compose

You can run the full application stack using `composes/application.yml`.

First build the service Docker images with Jib:

```bash
mvn clean compile jib:dockerBuild
```

Then start the application:

```bash
docker compose -f composes/application.yml up -d
```

Stop the application:

```bash
docker compose -f composes/application.yml down
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

## Keycloak

Start Keycloak locally:

```bash
docker run -p 8181:8080 \
  -e KC_BOOTSTRAP_ADMIN_USERNAME=admin \
  -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin \
  --name keycloak-dev \
  -d quay.io/keycloak/keycloak:26.4.5 start-dev
```

Keycloak admin console:

```text
http://localhost:8181
```

Default admin credentials:

- Username: `admin`
- Password: `admin`

## Zipkin

The services are configured to export traces to Zipkin at:

```text
http://localhost:9411/api/v2/spans
```

Start Zipkin locally:

```bash
docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin
```

Zipkin UI:

```text
http://localhost:9411
```

If Zipkin is not running, the services can still run, but you may see connection errors from the async Zipkin reporter.

## Suggested Startup Order

1. Start local infrastructure:

```bash
docker compose -f composes/mysql.yml up -d
docker compose -f composes/mongo.yml up -d
docker compose -f composes/kafka.yml up -d
```

2. Start Keycloak if gateway security is enabled.

3. Start Zipkin if you want distributed traces.

4. Start the Spring Boot services:

```bash
discovery-server
product-service
inventory-service
order-service
notification-service
api-gateway
```

Start `discovery-server` first so the other services can register with Eureka.

## Service Notes

- `product-service` uses MongoDB.
- `inventory-service` uses MySQL.
- `order-service` uses MySQL and publishes Kafka events.
- `notification-service` consumes Kafka events and logs order notifications.
- `api-gateway` routes traffic to backend services and integrates with Keycloak.
- `discovery-server` runs Eureka and should be available at `localhost:8761`.

## Useful Docker Commands

Check running containers:

```bash
docker ps
```

View logs for a dependency:

```bash
docker logs micro-mysql
docker logs micro-mongo
docker logs microservice-kafka
```

Restart a dependency:

```bash
docker restart micro-mysql
docker restart micro-mongo
docker restart microservice-kafka
```
