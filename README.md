# Employee Management System

A comprehensive Spring Boot application for managing employees and departments with role-based authentication and automated summary reporting.

## Features

- **CRUD Operations**: Complete REST API for Employee and Department management
- **Role-Based Security**: Two-tier authentication system (ADMIN/USER)
- **Automated Reporting**: Daily scheduled job for employee count summaries at 9 
- **Data Validation**: Comprehensive input validation and error handling
- **Database Integration**: postgres database with JPA/Hibernate
- **API Documentation**: Complete Postman collection included with all crud apis included

### Technology Stack
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security**
- **postgres Database**
- **Maven**

### Project Structure
```
src/
├── main/
│   ├── java/com/example/employeemanagementsystem/
│   │   ├── config/          # Security configuration
│   │   ├── controller/      # REST controllers
│   │   ├── model/           # JPA entities
│   │   ├── exception/       # Exception handling
│   │   ├── repository/      # Data repositories
│   │   ├── scheduler/       # Scheduled tasks
│   │   └── service/         # Business logic
│   └── resources/
│       ├── application.properties  # Application configuration
└── test/
    └── java/               # Unit tests
```

## Database Design

### Entity Relationship Diagram
- **ERD Diagram Included**

### Entities

#### Department
- `id` (Long, PK): Unique identifier
- `department_name` (String, Unique): Department name

#### Employee
- `id` (Long, PK): Unique identifier
- `staffId` (String ,Unique): Unique identifier
- `name` (String): Employee full name
- `email` (String, Unique): Employee email address
- `salary` (Double): Employee salary
- `hireDate` (Date): Date of hiring
- `department_id` (Long, FK): Reference to Department

#### DailySummary
- `id` (Long, PK): Unique identifier
- `summaryDate` (Date): Date of summary
- `departmentName` (String): Department name
- `employeeCount` (Long): Number of employees

### Relationships
- One Department can have many Employees (One-to-Many)
- Each Employee belongs to one Department (Many-to-One)

## Security Configuration

### Authentication
- **Type**: HTTP Basic Authentication
- **Storage**: In-memory user details

### Users & Roles
| Username | Password     | Role  | Permissions |
|----------|--------------|-------|-------------|
| admin    | adminpass    | ADMIN | Full CRUD access |
| employee | employeepass | USER  | Read-only access |

### Access Control
- **ADMIN Role**: All CRUD operations on employees and departments
- **USER Role**: Read-only access to GET endpoints
- **Unauthorized**: 401 Unauthorized response
- **Forbidden**: 403 Forbidden response

### Daily Summary Job
- **Schedule**: Every day at 9:00 AM (`0 0 9 * * *`)
- **Function**: Logs employee count per department and stores summary in `daily_summary` table

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- postgresSQL
- docker (if you want to run the whole stack in a container using docker )

### Installation & Running


1. **Clone the repository**
   ```bash
   git clone https://github.com/wesamwafa/Employee-Management-Service.git
   cd employee-management-service
   ```

2. **Build the application**
   ```bash
   mvn clean compile
   ```

3. **Run tests**
   ```bash
   mvn test
   ```

4. **Start the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   - API Base URL: `http://localhost:8080`

**You can run the whole stack (Spring Boot + PostgreSQL) using Docker Compose.**
   ```bash
   mvn clean package -DskipTests
   docker-compose up --build
   ```



## 📡 API Endpoints

### Department Endpoints
| Method | Endpoint                      | Role Required | Description |
|--------|-------------------------------|--------------|-------------|
| GET | `/departments`                | USER/ADMIN | Get all departments |
| GET | `/departments/{departmentId}` | ADMIN | Get department by ID |
| POST | `/departments`                | ADMIN | Create new department |
| PUT | `/departments/{departmentId}`           | ADMIN | Update department |
| DELETE | `/departments/{departmentId}`           | ADMIN | Delete department |

### Employee Endpoints
| Method | Endpoint               | Role Required | Description |
|--------|------------------------|--------------|-------------|
| GET | `/employees`           | USER/ADMIN | Get all employees |
| GET | `/employees/{staffId}` | ADMIN | Get employee by ID |
| POST | `/employees`           | ADMIN | Create new employee |
| PUT | `/employees/{staffId}` | ADMIN | Update employee |
| DELETE | `/employees/{staffId}` | ADMIN | Delete employee |

## Testing

### Unit Tests
- **Coverage**: Service layer business logic
- **Framework**: JUnit 5 + Mockito
- **Run**: `mvn test`

### API Testing
- **Tool**: Postman collection included
- 
### Manual Testing Examples

#### Get all departments (USER role)
```bash
curl -u employee:employeepass http://localhost:8080/departments
```

#### Create department (ADMIN role)
```bash
curl -u admin:adminpass -X POST -H "Content-Type: application/json" \
  -d '{"departmentName":"Research and Development"}' \
  http://localhost:8080/departments
```

#### Test unauthorized access
```bash
curl http://localhost:8080/employees
# Returns: 401 Unauthorized
```

## 🛡️ Exception Handling

### Global Exception Handler
- **BusinessException**: 400 Bad Request
- **NotAuthorizedException**: 401 Not Authorized
- **AuthorizationDeniedException**: 403 Forbidden access
- **Generic Exception**: 500 Internal Server Error

## 📋 Validation Rules

### Department
- departmentName: Required, 2-100 characters, unique

### Employee
- Name: Required, 2-100 characters
- staffId: Required, 2-9 characters , numbers
- Email: Required, valid email format, unique
- Salary: Required, positive number
- Hire Date: Required, not in future
- Department: Required, must exist

## 🔧 Configuration

### Application Properties (`application.proprties`)
```
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres?currentSchema={{employee_mgmt}}
spring.datasource.username={{postgres}}
spring.datasource.password={{1213}}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## Design Decisions

### 1. **Layered Architecture**
- **Controllers**: Handle HTTP requests/responses
- **Services**: Business logic and validation
- **Repositories**: Data access layer
- **Entities**: Domain models

### 2. **Security Implementation**
- **Basic Authentication**: Simple and effective for demo
- **In-Memory Users**: Suitable for development/testing
- **Role-Based Access**: Clear separation of permissions

### 3. **Exception Handling**
- **Global Handler**: Centralized error management
- **Consistent Responses**: Uniform error format
- **Detailed Validation**: Field-level error messages

### 4. **Database Design**
- **H2 In-Memory**: Fast development and testing
- **JPA/Hibernate**: ORM for database operations
- **Proper Relationships**: Foreign key constraints

### 5. **Scheduled Tasks**
- **Cron Expression**: Standard scheduling format
- **Logging**: Comprehensive activity tracking
- **Bonus Storage**: Audit trail in database

----
