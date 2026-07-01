# Barber Appointment Booking System - Java Spring Boot Backend

A REST API for managing barbershop appointments with role-based access control (Admin, Barber, User).

## Tech Stack
- **Java 21** with **Spring Boot 3.3**
- **MySQL 8.0** with JPA/Hibernate
- **Spring Security** with JWT authentication
- **Cloudinary** for image management
- **Docker & Docker Compose** for easy deployment
- **Maven** for dependency management

## Features
- ✅ User authentication & registration (Clients, Barbers, Admins)
- ✅ JWT-based authentication
- ✅ Appointment booking & cancellation
- ✅ Image upload to Cloudinary
- ✅ Role-based access control
- ✅ API versioning (`/api/v1/`)
- ✅ Global exception handling with custom error responses
- ✅ Jakarta Bean Validation

## Quick Start

### Prerequisites
- Java 21+
- Maven 3.9+
- Docker & Docker Compose
- Cloudinary account

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/sriram-d0/barber-appointment-api.git
   cd barber-appointment-api
   ```

2. **Configure environment variables**
   ```bash
   cp .env.example .env
   # Edit .env with your Cloudinary credentials and JWT secret
   ```

3. **Start MySQL with Docker Compose**
   ```bash
   docker-compose up -d
   ```

4. **Create database and tables**
   ```bash
   mysql -u root -p -h 127.0.0.1 < src/main/resources/db/schema.sql
   ```

5. **Build and run the application**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080/api/v1/`

## API Endpoints

### User APIs
- `POST /api/v1/user/auth/register` - Register new user
- `POST /api/v1/user/auth/login` - Login user
- `GET /api/v1/user/profile` - Get user profile (authenticated)
- `POST /api/v1/user/book-appointment` - Book appointment
- `POST /api/v1/user/cancel-appointment` - Cancel appointment
- `GET /api/v1/user/appointments` - List user appointments

### Admin APIs
- `POST /api/v1/admin/auth/login` - Admin login
- `POST /api/v1/admin/barber` - Add new barber (file upload)
- `GET /api/v1/admin/barbers` - Get all barbers
- `DELETE /api/v1/admin/barber/{id}` - Delete barber
- `GET /api/v1/admin/appointments` - Get all appointments
- `POST /api/v1/admin/appointment/cancel` - Cancel appointment
- `GET /api/v1/admin/dashboard` - Dashboard statistics

### Barber APIs
- `POST /api/v1/barber/auth/login` - Barber login
- `GET /api/v1/barber/appointments` - Get barber appointments
- `POST /api/v1/barber/slots` - Update available slots

## Project Structure

```
src/main/java/com/barbershop/
├── config/                  # Spring configs (Security, JWT, Cloudinary, CORS)
├── controller/              # REST endpoints (v1)
├── service/                 # Business logic
├── repository/              # Database access (JPA)
├── entity/                  # JPA entities (User, Barber, Appointment)
├── dto/                     # Data Transfer Objects
├── security/                # JWT & authentication
├── exception/               # Global exception handling
├── validation/              # Input validation
└── util/                    # Utility classes
```

## Configuration

Edit `src/main/resources/application.yml` to customize:
- Server port
- Database connection
- JWT settings
- Cloudinary credentials

## Testing

```bash
# Run tests
mvn test

# Run with coverage
mvn test jacoco:report
```

## Docker Deployment

```bash
# Build Docker image
docker build -t barber-api:latest .

# Run with docker-compose
docker-compose up
```

## Error Handling

All errors follow a consistent format:

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": [
    {
      "field": "email",
      "message": "Email must be valid"
    }
  ],
  "timestamp": "2024-06-29T10:30:00Z"
}
```

## Security

- Passwords are hashed with BCrypt
- JWT tokens expire after 24 hours
- CORS is configured for frontend integration
- Role-based access control via Spring Security

## License

ISC