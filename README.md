# BookStore API 
Bookstore Online is a demo ecommerce application that solely sells books of all types to it customers. Bookstore Online
buys its book stocks from Publisher through issuance of Purchase Order and receive delivered goods from supplying publisher
by accepting a Good Receipt document. The Goods Receipt document is used to validate the quantity supplied and upon verification
the delivered Books are stocked into the Books Online Inventory.

Bookstore Online sells books of the following types, FICTION, THRILLER, HORROR etc, Customer can browse available books in
stock and shop as they like. Bookstore Online provides a Shopping Cart to keep track of their customer items and Check Out feature
that enables making Payment for the total value of books in a shopping cart.

## Functional Requirement
Bookstore Online will provide the following capabilities that support and enable it to deliver its business operations digitally. These business operations include the following;

Inventory  Management:  is the business operation that manages the stock of available books, quantity sold and quantity to be placed on Purchase Order to restock inventory. Stocking the inventory is carried out by the Purchasing department head, where a Purchase Order is raised to capture the book titles and quantities to be placed on order. The purchase order is then delivered at Bookstore Online warehouse, where it is received by the head of the Inventory department.

The head of the inventory department also receives the Goods Receipt for the delivered order and uses the Goods Receipt to verify the delivered goods and updates the inventory for each book title. The Goods Receipt is also used to update the Bookstore Online inventory to sync the inventory status of both physical warehouse and the Bookstore inventory.

Book Search Facility:  The search functionality will allow books to be searched with different criteria such as Title, Author, Genre and Publication Year.

Order Management: is the business operation that follows through the customer shopping experience from the point of browsing, to placing items in their shopping cart, to checking out and to having the order delivered to the customer shipping address.

Customer Relationship Management: is the business process that manages information about Bookstore Online customers. As part of the process, customers are required to be registered on Bookstore Online to have an account opened for them. A registered account will have a digital ID which is an identity that abstracts the personal information away from the identity used for shopping on Bookstore Online. Registered users will be able to log into Bookstore Online to place orders for books and make payment.

Payment Integration: This is the functionality that facilitates making payment for purchase orders. The payment functionality enable customers to make payment using payment types such as USSD, Bank Transfer and Web Payment (Card)

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


