
# Inventory Management System

A backend Inventory Management System developed using Java, Spring Boot, and MySQL. The application provides RESTful APIs to manage products, suppliers, categories, stock movements, purchase orders, and inventory reports.

## Features

- Product CRUD operations
- Supplier and category management
- Stock-in and stock-out operations
- Purchase order creation and status management
- Automatic inventory updates when purchase orders are received
- Low-stock alerts based on reorder levels
- Inventory valuation and stock reporting
- Six-table relational database design
- RESTful API architecture
- Input validation and centralized exception handling
- JUnit 5 and Mockito unit testing
- Object-oriented and modular programming
- Maven-based dependency management

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Backend programming |
| Spring Boot 3 | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database access |
| Hibernate | Object-relational mapping |
| MySQL 8 | Relational database |
| Maven | Build and dependency management |
| JUnit 5 | Unit testing |
| Mockito | Mocking dependencies in tests |
| Git | Version control |
| Postman | API testing |

## Project Architecture

The application follows a layered architecture that separates controllers, business logic, database access, and error handling.

- Controller Layer: Handles HTTP requests and API responses.
- Service Layer: Implements inventory business logic.
- Repository Layer: Performs database operations using Spring Data JPA.
- Model Layer: Defines database entities and relationships.
- Exception Layer: Handles application errors and invalid requests.

## Project Structure

```text
InventoryManagementSystem/
├── src/
│   ├── main/
│   │   ├── java/com/inventory/
│   │   │   ├── controller/
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── SupplierController.java
│   │   │   │   ├── CategoryController.java
│   │   │   │   ├── StockController.java
│   │   │   │   └── PurchaseOrderController.java
│   │   │   ├── model/
│   │   │   │   ├── Product.java
│   │   │   │   ├── Supplier.java
│   │   │   │   ├── Category.java
│   │   │   │   ├── StockTransaction.java
│   │   │   │   ├── PurchaseOrder.java
│   │   │   │   └── PurchaseOrderItem.java
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   ├── exception/
│   │   │   └── InventoryManagementApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/inventory/
│           └── InventoryManagementApplicationTests.java
├── pom.xml
├── README.md
└── .gitignore
```

## Database Design

The application uses six relational tables managed through Hibernate and Spring Data JPA.

| Table | Description |
|---|---|
| categories | Stores product category information |
| suppliers | Stores supplier contact details |
| products | Stores product details, SKU, price, quantity, and reorder level |
| stock_transactions | Records stock-in and stock-out operations |
| purchase_orders | Stores purchase order information and status |
| purchase_order_items | Stores products, quantities, and unit costs for each order |

### Entity Relationships

- A product can be associated with a category and supplier.
- Each stock transaction references a product.
- Each purchase order references a supplier.
- A purchase order can contain multiple purchase order items.
- Each purchase order item references a product.

## Prerequisites

Install the following software:

- JDK 17 or later
- Apache Maven 3.8+
- MySQL Server 8+
- IntelliJ IDEA or Eclipse
- Postman (optional)

## Installation and Setup

### 1. Clone the Repository

```bash
git clone <your-github-repository-url>
cd InventoryManagementSystem
```

Alternatively, download and extract the project ZIP file.

### 2. Configure MySQL

Open `src/main/resources/application.properties` and configure your database credentials.

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/inventory_db?createDatabaseIfNotExist=true}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:root}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
```

Update the username and password according to your local MySQL configuration.

The database is configured for automatic creation when the MySQL user has the necessary permissions.

### 3. Build the Application

Run the following command from the project root directory:

```bash
mvn clean package
```

### 4. Run Unit Tests

```bash
mvn test
```

### 5. Start the Application

```bash
mvn spring-boot:run
```

The application runs at:

http://localhost:8080

The API base URL is:

http://localhost:8080/api

## REST API Documentation

### Product Management

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/products` | Retrieve all products |
| GET | `/api/products?search=keyboard` | Search products by name |
| GET | `/api/products/{id}` | Retrieve a product by ID |
| POST | `/api/products` | Create a product |
| PUT | `/api/products/{id}` | Update a product |
| DELETE | `/api/products/{id}` | Delete a product |
| GET | `/api/products/low-stock` | Retrieve low-stock products |

### Category Management

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/categories` | Retrieve all categories |
| GET | `/api/categories/{id}` | Retrieve a category by ID |
| POST | `/api/categories` | Create a category |
| PUT | `/api/categories/{id}` | Update a category |
| DELETE | `/api/categories/{id}` | Delete a category |

### Supplier Management

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/suppliers` | Retrieve all suppliers |
| GET | `/api/suppliers/{id}` | Retrieve a supplier by ID |
| POST | `/api/suppliers` | Create a supplier |
| PUT | `/api/suppliers/{id}` | Update a supplier |
| DELETE | `/api/suppliers/{id}` | Delete a supplier |

### Stock Management

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/stock/{productId}/adjust` | Perform stock-in or stock-out |
| GET | `/api/stock/transactions` | Retrieve stock transaction history |
| GET | `/api/stock/report` | Generate inventory report |
| GET | `/api/stock/low-stock` | Retrieve low-stock products |

### Purchase Order Management

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/purchase-orders` | Retrieve all purchase orders |
| GET | `/api/purchase-orders/{id}` | Retrieve purchase order details |
| POST | `/api/purchase-orders` | Create a purchase order |
| PATCH | `/api/purchase-orders/{id}/status?status=ORDERED` | Update purchase order status |
| DELETE | `/api/purchase-orders/{id}` | Delete an eligible purchase order |

## Example API Requests

Use Postman to test the endpoints after starting the application.

### Create a Category

**POST** `/api/categories`

```json
{
  "name": "Electronics",
  "description": "Electronic products and accessories"
}
```

### Create a Supplier

**POST** `/api/suppliers`

```json
{
  "name": "ABC Supplies",
  "email": "supplier@example.com",
  "phone": "+91-9876543210",
  "address": "Pune, Maharashtra"
}
```

### Create a Product

**POST** `/api/products`

Create the category and supplier first if you want to associate them with the product.

```json
{
  "sku": "KB-001",
  "name": "Mechanical Keyboard",
  "description": "USB mechanical keyboard",
  "unitPrice": 2499.00,
  "quantity": 20,
  "reorderLevel": 5,
  "category": {
    "id": 1
  },
  "supplier": {
    "id": 1
  }
}
```

### Stock-In Operation

**POST** `/api/stock/1/adjust`

```json
{
  "type": "STOCK_IN",
  "quantity": 10,
  "note": "New stock received"
}
```

### Stock-Out Operation

**POST** `/api/stock/1/adjust`

```json
{
  "type": "STOCK_OUT",
  "quantity": 2,
  "note": "Product dispatched"
}
```

The system checks stock availability and rejects stock-out requests when insufficient inventory is available.

### Create a Purchase Order

**POST** `/api/purchase-orders`

```json
{
  "supplierId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 10,
      "unitCost": 1800.00
    }
  ]
}
```

After creating the purchase order, update its status to `ORDERED`. Once the stock arrives, change the status to `RECEIVED`. The application then adds the ordered quantities to inventory and records stock transactions.

### Generate an Inventory Report

**GET** `/api/stock/report`

The response includes:

- Total product count
- Total units in stock
- Estimated inventory retail value
- Low-stock product count
- Low-stock product details

## Validation and Error Handling

The application implements:

- Required-field validation
- Non-negative product prices
- Non-negative stock quantities
- Positive stock adjustment quantities
- Supplier email validation
- Resource-not-found handling
- Duplicate-value and database constraint handling
- Meaningful HTTP error responses

## Testing

Execute the automated tests using:

```bash
mvn test
```

The included unit tests cover:

- Successful stock-in operations
- Rejection of stock-out operations when inventory is insufficient

JUnit 5 and Mockito are used to test core inventory business logic.

## Future Enhancements

- JWT authentication and role-based authorization
- Swagger/OpenAPI documentation
- Pagination, sorting, and advanced filtering
- Docker and Docker Compose deployment
- Database migrations using Flyway or Liquibase
- Inventory analytics dashboard
- Integration and end-to-end testing
- Audit logging and user activity tracking

## Author

**Rajesh Choudhary**

B.Tech in Computer Science and Engineering

GitHub: Add your GitHub profile URL

LinkedIn: Add your LinkedIn profile URL

## License

This project is developed for educational and portfolio purposes.
