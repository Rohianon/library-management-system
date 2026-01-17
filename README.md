# Library Management System

A full-stack library management application with a Spring Boot REST API backend and JavaFX desktop frontend.

## Tech Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Backend** | Spring Boot | 4.0.1 |
| | Spring Data JPA | 3.4.x |
| | H2 Database | In-memory |
| **Frontend** | JavaFX | 23 |
| | Jackson | 2.18.x |
| **Runtime** | Java | 25 |
| **Build** | Maven | 3.9+ |

## Features

- **CRUD Operations** - Create, Read, Update, Delete books
- **Real-time Search** - Filter books by title, author, or ISBN as you type
- **Pagination** - Navigate through large datasets with configurable page sizes
- **Form Validation** - Required field validation with error messages
- **Table Selection** - Click a row to populate form for editing

## Prerequisites

- **Java 25** - [Download from Oracle](https://www.oracle.com/java/technologies/downloads/) or use SDKMAN
- **Maven 3.9+** - [Download from Apache](https://maven.apache.org/download.cgi)

Verify installation:
```bash
java -version    # Should show Java 25
mvn -version     # Should show Maven 3.9+
```

## Project Structure

```
library-management-system/
├── backend/                          # Spring Boot REST API
│   ├── src/main/java/com/rohianon/library/
│   │   ├── LibraryApplication.java   # Application entry point
│   │   ├── controller/
│   │   │   └── BookController.java   # REST endpoints
│   │   ├── service/
│   │   │   └── BookService.java      # Business logic
│   │   ├── repository/
│   │   │   └── BookRepository.java   # Data access
│   │   ├── entity/
│   │   │   └── Book.java             # JPA entity
│   │   └── exception/
│   │       └── GlobalExceptionHandler.java
│   ├── src/main/resources/
│   │   └── application.properties    # Configuration
│   └── pom.xml
├── frontend/                         # JavaFX Desktop Application
│   ├── src/main/java/com/rohianon/library/fx/
│   │   ├── LibraryApp.java           # JavaFX entry point
│   │   ├── view/
│   │   │   └── MainView.java         # Main UI
│   │   ├── service/
│   │   │   └── BookService.java      # HTTP client
│   │   └── model/
│   │       ├── Book.java             # Data model
│   │       └── PageResponse.java     # Pagination model
│   ├── src/main/resources/
│   │   └── application.properties    # API configuration
│   └── pom.xml
└── README.md
```

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Rohianon/library-management-system.git
cd library-management-system
```

### 2. Start the Backend

```bash
cd backend
mvn spring-boot:run
```

> **Tip:** If `mvn` is not installed, use the Maven wrapper instead: `./mvnw spring-boot:run`

Wait for the message: `Started LibraryApplication in X seconds`

The API will be available at: `http://localhost:8080`

### 3. Start the Frontend

In a new terminal:

```bash
cd frontend
mvn javafx:run
```

> **Tip:** If `mvn` is not installed, use the Maven wrapper instead: `./mvnw javafx:run`

> **Note:** The backend must be running before starting the frontend.

## API Documentation

Base URL: `http://localhost:8080/api/books`

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/books` | Create a new book |
| `GET` | `/api/books` | Get all books |
| `GET` | `/api/books?page=0&size=10` | Get paginated books |
| `GET` | `/api/books/{id}` | Get a book by ID |
| `PUT` | `/api/books/{id}` | Update a book |
| `DELETE` | `/api/books/{id}` | Delete a book |

### Book Entity

| Field | Type | Constraints |
|-------|------|-------------|
| `id` | Long | Auto-generated |
| `title` | String | Required, max 255 chars |
| `author` | String | Required, max 255 chars |
| `isbn` | String | Required, max 20 chars, unique |
| `publishedDate` | LocalDate | Required (format: YYYY-MM-DD) |

### Request/Response Examples

#### Create a Book

```bash
# Using HTTPie
http POST http://localhost:8080/api/books \
  title="The Great Gatsby" \
  author="F. Scott Fitzgerald" \
  isbn="978-0743273565" \
  publishedDate="1925-04-10"

# Using curl
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "isbn": "978-0743273565",
    "publishedDate": "1925-04-10"
  }'
```

**Response (201 Created):**
```json
{
  "id": 1,
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "isbn": "978-0743273565",
  "publishedDate": "1925-04-10"
}
```

#### Get All Books

```bash
http GET http://localhost:8080/api/books
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "isbn": "978-0743273565",
    "publishedDate": "1925-04-10"
  }
]
```

#### Get Paginated Books

```bash
http GET "http://localhost:8080/api/books?page=0&size=10"
```

**Response (200 OK):**
```json
{
  "content": [...],
  "totalElements": 31,
  "totalPages": 4,
  "number": 0,
  "size": 10,
  "first": true,
  "last": false
}
```

#### Update a Book

```bash
http PUT http://localhost:8080/api/books/1 \
  title="The Great Gatsby (Updated)" \
  author="F. Scott Fitzgerald" \
  isbn="978-0743273565" \
  publishedDate="1925-04-10"
```

#### Delete a Book

```bash
http DELETE http://localhost:8080/api/books/1
```

**Response:** `204 No Content`

## Configuration

### Backend Configuration

File: `backend/src/main/resources/application.properties`

```properties
# H2 Database
spring.datasource.url=jdbc:h2:mem:librarydb
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

### Frontend Configuration

File: `frontend/src/main/resources/application.properties`

```properties
library.api.baseUrl=http://localhost:8080/api/books
```

**Environment Variable Override:**
```bash
export LIBRARY_API_BASE_URL=http://localhost:8080/api/books
```

## H2 Database Console

Access the database console at: `http://localhost:8080/h2-console`

**Connection Settings:**
- JDBC URL: `jdbc:h2:mem:librarydb`
- Username: `sa`
- Password: *(leave empty)*

## Troubleshooting

### Backend Issues

**Port 8080 already in use:**
```bash
# Find the process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>
```

**Maven build fails:**
```bash
# Clean and rebuild
mvn clean install -DskipTests
```

### Frontend Issues

**Cannot connect to backend:**
- Ensure the backend is running first
- Check that `http://localhost:8080/api/books` returns data
- Verify the `library.api.baseUrl` in application.properties

**JavaFX application won't start:**
```bash
# Ensure JavaFX Maven plugin is configured
mvn javafx:run -X  # Run with debug output
```

### Common Errors

| Error | Solution |
|-------|----------|
| `Connection refused` | Start the backend before the frontend |
| `Book not found with id: X` | The book was deleted or doesn't exist |
| `ISBN already exists` | Use a unique ISBN for each book |
| `Validation failed` | Ensure all required fields are filled |

## Development

### Running Tests

```bash
# Backend tests
cd backend
mvn test

# With coverage
mvn test jacoco:report
```

### Building for Production

```bash
# Backend JAR
cd backend
mvn clean package -DskipTests

# Frontend JAR
cd frontend
mvn clean package -DskipTests
```

## License

This project is for educational purposes.
