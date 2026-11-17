# Skill Experiment 2 - API Gateway, Eureka and Load Balancing

**Java:** 21  
**Spring Boot:** 3.4.5  
**Spring Cloud:** 2024.0.1  
**Build:** Maven

This is a four-module project:
- `eureka-server` - service registry, port 8761
- `product-service` - product REST API, default port 8081
- `cart-service` - cart REST API, port 8083
- `api-gateway` - single entry point, port 8080

## Prerequisites
1. Install JDK 21 and Maven.
2. Check in Command Prompt:
   ```bash
   java -version
   mvn -version
   ```
   Both should use Java 21.

## Build
Open Command Prompt in the folder containing the root `pom.xml` and run:
```bash
mvn clean package
```

## Run the applications
Open **five separate terminals** in the project root. Run each command in a separate terminal.

Terminal 1 - Eureka Server:
```bash
mvn -pl eureka-server spring-boot:run
```

Terminal 2 - Product Service instance 1:
```bash
mvn -pl product-service spring-boot:run
```

Terminal 3 - Product Service instance 2 (same service, different port):
```bash
mvn -pl product-service spring-boot:run -Dspring-boot.run.arguments=--server.port=8082
```

Terminal 4 - Cart Service:
```bash
mvn -pl cart-service spring-boot:run
```

Terminal 5 - API Gateway:
```bash
mvn -pl api-gateway spring-boot:run
```

Wait a short time for the services to register with Eureka.

## Test URLs

All service requests below go through the API Gateway:

| Purpose | Method | URL |
|---|---|---|
| List products | GET | http://localhost:8080/products |
| Product by ID | GET | http://localhost:8080/products/1 |
| View cart | GET | http://localhost:8080/cart |
| Add to cart demo | POST | http://localhost:8080/cart |

Eureka Dashboard:
http://localhost:8761

Expected registered applications:
- `PRODUCT-SERVICE` (two instances: ports 8081 and 8082)
- `CART-SERVICE`
- `API-GATEWAY`

## Verify load balancing
Send `GET http://localhost:8080/products` several times. Check the `instancePort` field in the JSON response. Requests should be distributed between 8081 and 8082. The exact sequence is not guaranteed.

Example response:
```json
{
  "service": "Product Service",
  "instancePort": "8081",
  "products": [
    {"id": 1, "name": "Laptop", "price": 55000},
    {"id": 2, "name": "Mobile", "price": 20000},
    {"id": 3, "name": "Headphones", "price": 2000}
  ]
}
```

Example cart response:
```json
{
  "service": "Cart Service",
  "items": [
    {"product": "Mobile", "quantity": 1, "price": 20000},
    {"product": "Headphones", "quantity": 1, "price": 2000}
  ],
  "totalAmount": 22000
}
```

## Important
- The gateway uses `lb://PRODUCT-SERVICE`, so it discovers product instances through Eureka and Spring Cloud LoadBalancer.
- Do not add `spring-boot-starter-web` to the API Gateway module; this project uses the reactive Spring Cloud Gateway.
- These are demonstration endpoints with sample in-memory data; no database is required.
- If Maven reports a dependency download error, check your internet connection and run `mvn -U clean package`.
