Fashion E-Commerce Website

A full-stack fashion e-commerce web application built using Spring Boot, Thymeleaf, MySQL, and Spring Data JPA. The application provides product browsing, product variants, shopping cart, checkout, order management, and an admin dashboard.

Features
Customer Features
User registration and login
View available fashion products
Search products
View product details
Select product variants such as size and color
Add products to cart
Update cart quantity
Remove products from cart
View cart total
Checkout
Place orders
View order history
View detailed order information
Admin Features
Admin dashboard
View products
Add products
Edit products
Delete products
Manage product information
Technologies Used
Java
Spring Boot
Spring MVC
Spring Data JPA
Spring Security
Thymeleaf
MySQL
HTML5
CSS3
JavaScript
Maven
IntelliJ IDEA
Project Structure
src/
 └── main/
     ├── java/
     │   └── com.geetanjali.fashionecommerce/
     │       ├── controller/
     │       ├── entity/
     │       ├── repository/
     │       ├── service/
     │       └── DataInitializer.java
     │
     └── resources/
         ├── templates/
         ├── static/
         └── application.properties
Database

The project uses MySQL for storing:

Users
Categories
Brands
Products
Product variants
Cart
Cart items
Orders
Order items
How to Run
1. Clone the repository
git clone <your-github-repository-url>
2. Open the project

Open the project in IntelliJ IDEA.

3. Configure MySQL

Create a MySQL database and update the database configuration in:

src/main/resources/application.properties

Example:

spring.datasource.url=jdbc:mysql://localhost:3306/fashion_ecommerce
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
4. Run the application

Run:

FashionEcommerceApplication.java

Then open:

http://localhost:8080
Main Application Flow
Home
  ↓
Products
  ↓
Product Details
  ↓
Select Variant
  ↓
Add to Cart
  ↓
Cart
  ↓
Checkout
  ↓
Place Order
  ↓
My Orders
  ↓
Order Details
Admin Flow
Admin Dashboard
      ↓
   Products
   ↙  ↓  ↘
 Add  Edit  Delete
Project Outcome

The project successfully implements the core functionality of a fashion e-commerce platform, including product management, shopping cart, checkout, order processing, and administration.

Author

Geetanjali

Information Science Engineering
