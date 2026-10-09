# Vendor Management System

A Java-based desktop application developed using Java Swing and MySQL
to manage vendor information, track purchases and sales, and calculate
business profit and loss.

## Project Overview

The Vendor Management System provides a simple graphical interface
for managing vendor details and maintaining transaction records.

The application connects to a MySQL database using JDBC to store
and retrieve information.

## Features

- Vendor Registration and Management
- Vendor Information Storage
- Purchase Transaction Management
- Sales Transaction Management
- MySQL Database Integration
- Profit and Loss Calculation
- Graphical User Interface using Java Swing
- Database Connectivity using JDBC

## Technologies Used

| Technology | Purpose |
|------------|---------|
| Java | Application development |
| Java Swing | Graphical user interface |
| JDBC | Database connectivity |
| MySQL | Database management |
| Eclipse IDE | Development environment |
| Git and GitHub | Version control and project hosting |

## Project Structure

VendorManagementSystem/
|
|-- src/
|   |-- DBConnection.java
|   |-- DBTest.java
|   |-- MainDashboard.java
|   |-- ProfitLossForm.java
|   |-- VendorForm.java
|
|-- sql/
|   |-- schema.sql
|
|-- .gitignore
|-- README.md

## Database Schema

The application uses MySQL to store vendor and transaction information.

The database includes tables for managing:

- Vendors
- Purchases
- Sales
- Payments

The SQL script is available in the `sql` directory.

## Database Setup

1. Install MySQL Server and MySQL Workbench.
2. Open MySQL Workbench and connect to your local MySQL server.
3. Open the `sql/schema.sql` file.
4. Execute the SQL script to create the database and tables.
5. Verify that the database and tables have been created successfully.

## Configuration

Configure your MySQL connection in `DBConnection.java`.

Update the following settings to match your local environment:

- Database URL
- MySQL username
- MySQL password

For security, avoid hardcoding database passwords in source code.
Use environment variables or another local configuration method.

## How to Run

1. Install the Java Development Kit (JDK).
2. Install Eclipse IDE.
3. Clone or download this repository.
4. Import the project into Eclipse.
5. Configure the MySQL JDBC driver.
6. Create the database using `sql/schema.sql`.
7. Configure your database connection.
8. Open `MainDashboard.java`.
9. Run the application as a Java Application.

## Learning Outcomes

- Java object-oriented programming
- Desktop GUI development using Swing
- JDBC database connectivity
- Relational database design using MySQL
- SQL table creation and relationships
- Transaction management
- Basic financial calculations
- Version control using Git and GitHub

## Future Enhancements

- Advanced inventory tracking
- Vendor payment history and outstanding balance tracking
- Improved financial reports
- Data export to PDF or Excel
- Enhanced dashboard analytics
- Improved input validation and error handling

## Author

Rohit Birudukota

## Repository

GitHub:
https://github.com/rohitbirudukota/VendorManagementSystem

---

Developed as an academic project using Java Swing and MySQL.
