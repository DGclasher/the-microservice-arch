# Microservice Architecture with Spring Boot

This project demonstrates a clean microservice architecture built using **Spring Boot**, **Spring Cloud**, and **Docker**.  
It includes fully independent services communicating through an API Gateway and registered with Eureka Discovery Server.

##  Overview

**Services included:**
- `product-service` – Manages product data
- `inventory-service` – Tracks stock availability
- `order-service` – Places orders by calling other services
- `api-gateway` – Single entry point for all clients
- `discovery-server` – Eureka service registry

All services run independently and communicate using **service discovery** and **load-balanced REST calls**.

### Keycloak OAuth
To start keycloak oauth, run the following
```declarative
docker run -p 8181:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin --name keycloak-dev -d quay.io/keycloak/keycloak:26.4.5 start-dev
```