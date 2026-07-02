# CivicSolve (CrudMVC)

CivicSolve is a community-driven civic issue management system. It allows citizens to report problems, explore active issues, and engage with community-driven solutions in a specific geographical area (Vadodara). The platform features robust user authentication, role-based access control, map integration, and an engaging "Hypes" system to track issue popularity.

## Features

- **Issue Management**: Report, explore, and resolve civic issues.
- **Geofencing & Map Integration**: Integrated map functionality restricted to the Vadodara area to enforce local civic issue reporting.
- **User Roles & Profiles**: Role-based access control (e.g., Citizens) with comprehensive profile management.
- **Engagement**: "Hypes" system to gauge community interest and prioritize problems.
- **Security**: Secure authentication using JWT (JJWT) and password hashing (jBCrypt), alongside strict form validations.

## Tech Stack

- **Backend Framework**: Java 17, Spring Web MVC (5.3.31), Spring JDBC
- **Database**: PostgreSQL with Stored Procedures for performance optimization
- **Frontend / Views**: JSP, JSTL
- **Security / Auth**: JSON Web Tokens (JJWT), jBCrypt
- **Build Tool**: Maven

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17
- Apache Maven
- PostgreSQL Database
- Apache Tomcat (or another compatible servlet container)

### Installation

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd CrudMVC
   ```

2. **Configure Database:**
   Ensure PostgreSQL is running and update your database credentials within the Spring application configuration. Setup the necessary schemas, tables, and stored procedures.

3. **Build the project:**
   ```bash
   mvn clean install
   ```

4. **Deploy:**
   Deploy the generated `CrudMVC-1.0-SNAPSHOT.war` (found in the `target/` directory) to your Apache Tomcat server.

5. **Run:**
   Start Tomcat and access the application via your browser at `http://localhost:8080/CrudMVC`.

## Docker (Optional)

The project includes a `Dockerfile` and `docker-compose.yml` for containerized deployment.

1. Ensure Docker and Docker Compose are installed.
2. Run the application:
   ```bash
   docker-compose up -d
   ```
