# Library Management System

A full-stack library management application with a Spring Boot REST API backend and JavaFX desktop frontend.

## Tech Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Backend** | Spring Boot | 4.0.1 |
| | Spring Framework | 6.2.x |
| | Spring Data JPA | 3.4.x |
| | H2 Database | (managed) |
| **Frontend** | JavaFX | 23 |
| | Jackson | 2.18.x |
| **Runtime** | Java | 25 |
| **Build** | Maven | 3.9+ |

## Project Structure

```
library-management-system/
├── backend/                 # Spring Boot REST API
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/       # Java source files
│   │   │   └── resources/  # Application properties
│   │   └── test/           # Unit tests
│   └── pom.xml
├── frontend/               # JavaFX desktop application
│   ├── src/
│   │   └── main/
│   │       ├── java/       # Java source files
│   │       └── resources/  # FXML, CSS
│   └── pom.xml
├── .gitignore
└── README.md
```

## Prerequisites

- **Java 25** - [Download from Oracle](https://www.oracle.com/java/technologies/downloads/) or use SDKMAN
- **Maven 3.9+** - [Download from Apache](https://maven.apache.org/download.cgi)

Verify installation:
```bash
java -version    # Should show Java 25
mvn -version     # Should show Maven 3.9+
```

## Running the Application

### Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

H2 Console: `http://localhost:8080/h2-console`

### Frontend (JavaFX)

```bash
cd frontend
mvn javafx:run
```

> **Note:** The backend must be running before starting the frontend.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/books` | Create a new book |
| `GET` | `/api/books` | Get all books |
| `PUT` | `/api/books/{id}` | Update a book |
| `DELETE` | `/api/books/{id}` | Delete a book |

## Features

### Core Features
- View all books in a table
- Add new books
- Edit existing books
- Delete books
- Refresh data from server

### Bonus Features
- Search/filter books by title or author
- Pagination support

## License

This project is for educational purposes.
