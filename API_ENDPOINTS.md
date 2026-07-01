# API Endpoints Reference

## Base URL
`http://localhost:8080/api/v1`

## Authentication Endpoints

### User Registration
- **POST** `/user/auth/register`
- **Body**:
  ```json
  {
    "userName": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "phone": "1234567890"
  }
  ```
- **Response**: JWT Token + User Profile

### User Login
- **POST** `/user/auth/login`
- **Body**:
  ```json
  {
    "email": "john@example.com",
    "password": "password123"
  }
  ```
- **Response**: JWT Token

### Barber Login
- **POST** `/barber/auth/login`
- **Body**: Same as user login
- **Response**: JWT Token

### Admin Login
- **POST** `/admin/auth/login`
- **Body**: Same as user login
- **Response**: JWT Token

## User Endpoints (Requires Authentication)

### Get User Profile
- **GET** `/user/profile`
- **Header**: `Authorization: Bearer <token>`
- **Response**: User profile data

### Book Appointment
- **POST** `/user/book-appointment`
- **Header**: `Authorization: Bearer <token>`
- **Body**:
  ```json
  {
    "userId": 1,
    "barberId": 1,
    "slotDate": "2024-06-30",
    "slotTime": "10:00 AM",
    "message": "Looking forward to it"
  }
  ```
- **Response**: Appointment details

### Cancel Appointment
- **POST** `/user/cancel-appointment`
- **Header**: `Authorization: Bearer <token>`
- **Body**:
  ```json
  {
    "userId": 1,
    "appointmentId": 5
  }
  ```
- **Response**: Success message

### Get User Appointments
- **GET** `/user/appointments/{userId}`
- **Header**: `Authorization: Bearer <token>`
- **Response**: List of appointments

## Barber Endpoints (Requires Authentication)

### Get Barber Appointments
- **GET** `/barber/appointments/{barberId}`
- **Header**: `Authorization: Bearer <token>`
- **Response**: List of barber's appointments

## Admin Endpoints (Requires Admin Role)

### Add Barber
- **POST** `/admin/barber`
- **Header**: `Authorization: Bearer <token>`
- **Body** (multipart/form-data):
  - `barber`: JSON with name, email, password, about
  - `image`: Image file
- **Response**: Barber details

### Get All Barbers
- **GET** `/admin/barbers`
- **Header**: `Authorization: Bearer <token>`
- **Response**: List of all barbers

### Delete Barber
- **DELETE** `/admin/barber/{id}`
- **Header**: `Authorization: Bearer <token>`
- **Response**: Success message

### Get All Appointments
- **GET** `/admin/appointments`
- **Header**: `Authorization: Bearer <token>`
- **Response**: List of all appointments

### Cancel Appointment (Admin)
- **POST** `/admin/appointment/cancel`
- **Header**: `Authorization: Bearer <token>`
- **Body**:
  ```json
  {
    "appointmentId": 5
  }
  ```
- **Response**: Success message

### Get Dashboard
- **GET** `/admin/dashboard`
- **Header**: `Authorization: Bearer <token>`
- **Response**:
  ```json
  {
    "totalUsers": 10,
    "totalBarbers": 5,
    "totalAppointments": 20,
    "cancelledAppointments": 2,
    "completedAppointments": 15
  }
  ```

## Error Codes

- **200**: Success
- **201**: Created
- **400**: Bad Request (validation error)
- **401**: Unauthorized (invalid credentials)
- **404**: Not Found
- **500**: Internal Server Error

## Common Error Responses

### Validation Error
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

### Authentication Error
```json
{
  "status": 401,
  "message": "Invalid email or password",
  "timestamp": "2024-06-29T10:30:00Z"
}
```

### Resource Not Found
```json
{
  "status": 404,
  "message": "User not found",
  "timestamp": "2024-06-29T10:30:00Z"
}
```
