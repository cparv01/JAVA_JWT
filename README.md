# SpringBootCrud

## Project Overview

**SpringBootCrud** is a Spring Boot-based application that implements a simple CRUD (Create, Read, Update, Delete) operation for managing user and admin sessions. The application uses JWT (JSON Web Token) authentication to secure the user and admin sessions. It supports handling user management features such as registration, login, and session management, while separating the roles of **Admin** and **User**.

The system allows users to perform CRUD operations on resources and provides different permissions for admins and regular users, which are validated through JWT tokens.

## Features

- **User Registration**: Allows new users to register by providing their credentials (username, email, password).
- **User Login**: Authentication using username/email and password to generate a JWT token.
- **Role-based Access Control**: Admins and users have different levels of access. JWT tokens validate the roles for access control.
- **CRUD Operations**:
  - **Create**: Allows admins or users to create resources (based on permissions).
  - **Read**: Admins can read all data, while regular users can read their own data.
  - **Update**: Admins can update any data, while users can only update their own information.
  - **Delete**: Admins can delete any data, while users can only delete their own data.
- **JWT Authentication**: Secure authentication using JWT tokens to authorize requests.

## Technologies Used

- **Spring Boot**: Framework for building the backend of the application.
- **Spring Security**: Used for handling authentication and authorization.
- **JWT**: Used for securing API endpoints with token-based authentication.
- **H2 Database**: In-memory database for data storage (can be replaced with another database like MySQL).
- **Lombok**: Used to reduce boilerplate code.
- **Maven**: For project management and dependency management.

## Installation

### Prerequisites

Make sure you have the following installed:

- JDK 11 or later
- Maven
- Any IDE (e.g., IntelliJ IDEA, Eclipse)

### Steps to run the application

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/springbootcrud.git
