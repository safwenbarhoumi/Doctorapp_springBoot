# TP Spring Boot CRUD

## Description
This project is a simple CRUD (Create, Read, Update, Delete) application built with Spring Boot. It provides RESTful APIs to manage entities with basic database operations.

## Prerequisites
Before running the project, ensure you have the following installed:
- Java 17 or later
- Maven 3+
- MySQL or PostgreSQL (or another database of your choice)

## Dependencies Used
The project uses the following dependencies:
```xml
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Spring Boot Starter Security (Optional) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- MySQL Driver -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

## Installation & Running the Project
Follow these steps to install and run the project:

```bash
# Clone the repository
git clone https://github.com/your-repository/TP-_SpringBoot-CRUD.git
cd TP-_SpringBoot-CRUD

# Configure database in application.properties or application.yml
# Example for MySQL in src/main/resources/application.properties
# spring.datasource.url=jdbc:mysql://localhost:3306/your_database
# spring.datasource.username=root
# spring.datasource.password=your_password

# Clean and build the project
mvn clean install

# Run the project
mvn spring-boot:run
```

## API Endpoints
The following endpoints are available:

| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/entities` | Get all entities |
| GET | `/api/entities/{id}` | Get entity by ID |
| POST | `/api/entities` | Create a new entity |
| PUT | `/api/entities/{id}` | Update an existing entity |
| DELETE | `/api/entities/{id}` | Delete an entity |


