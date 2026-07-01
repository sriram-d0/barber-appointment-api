# Setup Instructions for Barber Appointment API

## Prerequisites
- Java 21 JDK
- Maven 3.9+
- Docker & Docker Compose
- MySQL Client (optional, for manual DB management)
- Postman or similar API testing tool

## Installation Steps

### 1. Clone the Repository
```bash
git clone https://github.com/sriram-d0/barber-appointment-api.git
cd barber-appointment-api
```

### 2. Configure Environment Variables
```bash
cp .env.example .env
```

Edit `.env` with your values:
```env
SERVER_PORT=8080
DB_URL=jdbc:mysql://localhost:3306/barber_db
DB_USERNAME=root
DB_PASSWORD=root
JWT_SECRET=your-secure-secret-key-minimum-256-bits
JWT_EXPIRATION=86400000


### 3. Start MySQL with Docker Compose
```bash
# Start only MySQL
docker-compose up -d mysql

# Wait for MySQL to be healthy (check logs)
docker-compose logs mysql
```

### 4. Create Database and Tables
```bash
# Option 1: Using Docker MySQL client
docker-compose exec mysql mysql -u root -proot < src/main/resources/db/schema.sql

# Option 2: Using local MySQL client
mysql -u root -proot -h 127.0.0.1 < src/main/resources/db/schema.sql
```

### 5. Build the Application
```bash
mvn clean install
```

### 6. Run the Application
```bash
# Option 1: Using Maven
mvn spring-boot:run

# Option 2: Using Java directly
java -jar target/barber-appointment-api-1.0.0.jar

# Option 3: Using Docker Compose (complete stack)
docker-compose up --build
```

The API will be available at: `http://localhost:8080/api/v1/`

## Testing the API

### Default Admin Credentials
```
Email: admin@barbershop.com
Password: admin@123
```

### User Registration
```bash
curl -X POST http://localhost:8080/api/v1/user/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "phone": "1234567890"
  }'
```

### User Login
```bash
curl -X POST http://localhost:8080/api/v1/user/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Admin Login
```bash
curl -X POST http://localhost:8080/api/v1/admin/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@barbershop.com",
    "password": "admin@123"
  }'
```

### Get Dashboard (Admin Only)
```bash
curl -X GET http://localhost:8080/api/v1/admin/dashboard \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Book Appointment
```bash
curl -X POST http://localhost:8080/api/v1/user/book-appointment \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "userId": 1,
    "barberId": 1,
    "slotDate": "2024-06-30",
    "slotTime": "10:00 AM",
    "message": "Looking forward to the appointment"
  }'
```

## Project Structure

```
barber-appointment-api/
├── pom.xml                          # Maven configuration
├── docker-compose.yml               # Docker Compose setup
├── Dockerfile                       # Docker image build
├── .env.example                     # Environment variables template
├── .gitignore                       # Git ignore file
├── README.md                        # Project overview
├── SETUP.md                         # This file
├── src/main/
│   ├── java/com/barbershop/
│   │   ├── BarberAppointmentApplication.java    # Main entry point
│   │   ├── config/                              # Configuration classes
│   │   │   ├── SecurityConfig.java
│   │   │   ├── CorsConfig.java
│   │   │   └── CloudinaryConfig.java
│   │   ├── controller/                          # REST endpoints
│   │   │   ├── UserController.java
│   │   │   ├── BarberController.java
│   │   │   └── AdminController.java
│   │   ├── service/                             # Business logic
│   │   │   ├── UserAuthService.java
│   │   │   ├── BarberAuthService.java
│   │   │   ├── AdminAuthService.java
│   │   │   ├── AppointmentService.java
│   │   │   └── CloudinaryService.java
│   │   ├── repository/                          # Database access
│   │   │   ├── UserRepository.java
│   │   │   ├── BarberRepository.java
│   │   │   ├── AppointmentRepository.java
│   │   │   └── AdminRepository.java
│   │   ├── entity/                              # JPA entities
│   │   │   ├── User.java
│   │   │   ├── Barber.java
│   │   │   ├── Appointment.java
│   │   │   └── Admin.java
│   │   ├── dto/                                 # Data Transfer Objects
│   │   │   ├── UserRegistrationDTO.java
│   │   │   ├── LoginRequestDTO.java
│   │   │   ├── AuthResponseDTO.java
│   │   │   ├── AppointmentDTO.java
│   │   │   └── ... (other DTOs)
│   │   ├── security/                            # JWT & authentication
│   │   │   ├── JwtTokenProvider.java
│   │   │   └── JwtAuthenticationFilter.java
│   │   ├── exception/                           # Exception handling
│   │   │   ├── ErrorResponse.java
│   │   │   ├── ResourceNotFoundException.java
│   │   │   ├── BadRequestException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   └── util/                                # Utility classes
│   │       └── ResponseUtil.java
│   └── resources/
│       ├── application.yml                      # Main config
│       ├── db/
│       │   └── schema.sql                       # Database schema
│       └── db/data.sql                          # (Optional) Sample data
└── src/test/
    └── java/com/barbershop/                     # Unit tests
```

## Troubleshooting

### MySQL Connection Failed
```bash
# Check if MySQL container is running
docker-compose ps

# View MySQL logs
docker-compose logs mysql

# Restart MySQL
docker-compose restart mysql
```

### Application Won't Start
```bash
# Check Java version
java -version

# Verify Maven is installed
mvn -v

# Clean and rebuild
mvn clean install -U
```

### JWT Token Issues
- Ensure `JWT_SECRET` is set and at least 256 characters
- Token format should be: `Bearer <token>` in Authorization header
- Check token expiration: default is 24 hours

### Cloudinary Upload Fails
- Verify Cloudinary credentials are correct
- Check API key and secret in `.env`
- Ensure file size is under 10MB (configured in application.yml)

## Development Tips

### Run Tests
```bash
mvn test
```

### View SQL Statements (Debug Mode)
Set in `application.yml`:
```yaml
spring:
  jpa:
    properties:
      hibernate:
        show_sql: true
        format_sql: true
```

### Hot Reload During Development
```bash
mvn spring-boot:run -Dspring-boot.run.arguments='--spring.devtools.restart.enabled=true'
```

### Build Docker Image
```bash
docker build -t barber-api:latest .
```

## API Documentation

### Authentication
All authenticated endpoints require the `Authorization` header:
```
Authorization: Bearer <JWT_TOKEN>
```

### Response Format
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... }
}
```

### Error Response Format
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

## Security Notes

- **Passwords**: Stored using BCrypt with salt
- **JWT Secret**: Change `JWT_SECRET` in production (minimum 256 bits)
- **CORS**: Currently allows all origins, restrict in production
- **Database**: Use strong passwords in production
- **Cloudinary**: Keep API secret secure, use environment variables

## Deployment

### Docker Deployment
```bash
# Build and push to Docker Hub
docker build -t yourusername/barber-api:latest .
docker push yourusername/barber-api:latest

# Run on production server
docker-compose -f docker-compose.yml up -d
```

### Health Check
```bash
curl http://localhost:8080/api/v1/health
```

## Support

For issues or questions, please open an issue on GitHub:
https://github.com/sriram-d0/barber-appointment-api/issues
