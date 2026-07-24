# E-Commerce Spring Cloud API Gateway

An e-commerce backend built with Spring Boot. This project combines SQL and MongoDB to handle different business domains.

## System Architecture

The system consists of an API Gateway, Service Discovery, and two main business domains running on separate databases.

### Infrastructure
* **Eureka Server** 
* **API Gateways**

### 1. Transaction (SQL Database)
* `auth-service`:
* `user-service`: 
* `order-service`:
* `payment-service`:

### 2. Catalog (MongoDB)
* `product-service`: 
* `inventory-service`: 
* `review-service`: 
* `shipping-service`:

### Prerequisites
* Java 17+ 
* Maven
* MySQL / PostgreSQL (for the Transaction)
* MongoDB (for the Catalog)
