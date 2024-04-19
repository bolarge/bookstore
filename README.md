# BookStore API 
Bookstore Online is a demo ecommerce application that solely sells books of all types to it customers. Bookstore Online
buys its book stocks from Publisher through issuance of Purchase Order and receive delivered goods from supplying publisher
by accepting a Good Receipt document. The Goods Receipt document is used to validate the quantity supplied and upon verification
the delivered Books are stocked into the Books Online Inventory.

Bookstore Online sells books of the following types, FICTION, THRILLER, HORROR etc, Customer can browse available books in
stock and shop as they like. Bookstore Online provides a Shopping Cart to keep track of their customer items and Check Out feature
that enables making Payment for the total value of books in a shopping cart.

## Functional Requirement
1. Ability to provide secure user management and other domain resources
2. Ability to stock books in the Inventory
3. Ability to Add Book Items to Shopping cart
4. Ability to Remove Book Item from Shopping cart
5. Ability to show Items in the Shopping cart
6. Ability to Check out the Shopping cart

## System Requirements
* Java 21
* Mysql 8
* Maven
* Springboot 3.2
* Spring Web MVC
* Spring Data JPA
* Docker
* Docker Compose

## Getting Started with Deployment
Application is a monolith maven project with no internal sub modules. It inherits from springboot-parent 

### Database Setup
Hibernate ddl.auto flag is set to update, therefore, bookstore_db database is to be created. Run below code
from any mysql client terminal
```properties
CREATE DATABASE bookstore_db;
```

#### Run locally
Start server using
```bash
mvn spring-boot:run
```

To view specs, visit http://localhost:8586/bookstore/swagger-ui/index.html

#### Run locally via docker compose
##### Requirement: Ensure your Docker-daemon is up and running. Docker Desktop.
```bash
  cd bookstore
  copy the compose.yml to the dir
  docker compose up
```

##### Run Docker login
```bash
    docker login -u <username>
```


