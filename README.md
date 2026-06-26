# Hostel/PG Management System

A comprehensive full-stack web application for managing hostels and paying guest accommodations with role-based access control.

## 🎯 Project Overview

The Hostel Management System is designed to streamline hostel operations with features for owners, wardens, students, and security guards. Built with Spring Boot backend and React frontend, it provides a modern, secure, and scalable solution for hostel management.

## 🚀 Features Implemented

### ✅ Phase 1: Authentication & User Management (Completed)
- User registration with role selection (OWNER, WARDEN, STUDENT, GUARD)
- JWT-based authentication
- Login/Logout functionality
- Protected routes with role-based access control
- User profile management
- Password change functionality

### ✅ Week 2: Hostel & Room Management (Completed)
- **Hostel Management**
  - Create, read, update, and delete hostels (OWNER only)
  - View all hostels (all authenticated users)
  - Hostel details page with statistics
  - Assign wardens to hostels
  - Manage hostel amenities and images
  
- **Room Management**
  - Add rooms to hostels with detailed information
  - Room types: Single, Double, Triple, Dormitory
  - Track room capacity and occupancy
  - Room status management (Available, Occupied, Maintenance, Reserved)
  - Room amenities and pricing
  - Floor-wise room organization

### 🔄 Upcoming Features

#### Week 3: Booking & Payment Management
- Room booking system for students
- Booking approval workflow
- Payment tracking and history
- Payment status management
- Receipt generation

#### Week 4: Complaints, Visitors & Mess Management
- Complaint management system
- Visitor registration and check-in/out
- Mess management features
- Maintenance request tracking

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.2.0
- **Database**: MySQL
- **Security**: Spring Security with JWT
- **Authentication**: JSON Web Tokens (JWT)
- **Build Tool**: Maven
- **Java Version**: 17

### Frontend
- **Framework**: React 18.2.0
- **Routing**: React Router DOM 6.20.0
- **HTTP Client**: Axios 1.6.2
- **Styling**: Tailwind CSS 3.3.5
- **State Management**: React Context API

## 📦 Project Structure

```
hostel-management-backend/
├── src/
│   └── main/
│       ├── java/com/hostelmgmt/
│       │   ├── config/          # Security & JWT configuration
│       │   ├── controller/      # REST API controllers
│       │   ├── dto/             # Data Transfer Objects
│       │   ├── exception/       # Custom exceptions & handlers
│       │   ├── model/           # JPA entities
│       │   ├── repository/      # Data access layer
│       │   ├── service/         # Business logic
│       │   └── util/            # JWT utilities
│       └── resources/
│           └── application.properties

hostel-management-frontend/
├── public/
├── src/
│   ├── components/
│   │   └── common/              # Reusable components (Navbar, ProtectedRoute)
│   ├── context/                 # React Context (Auth)
│   ├── pages/                   # Page components
│   │   ├── hostels/             # Hostel management pages
│   │   └── rooms/               # Room management pages
│   ├── services/                # API service functions
│   ├── App.js                   # Main app component
│   └── index.css                # Tailwind CSS imports
└── package.json
```

## 🔧 API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### User Management
- `GET /api/users/me` - Get current user profile
- `GET /api/users/{userId}` - Get user by ID
- `PUT /api/users/profile` - Update user profile

### Hostel Management
- `POST /api/hostels` - Create hostel (OWNER only)
- `GET /api/hostels` - Get all active hostels
- `GET /api/hostels/my-hostels` - Get hostels owned by current user (OWNER only)
- `GET /api/hostels/{hostelId}` - Get hostel by ID
- `PUT /api/hostels/{hostelId}` - Update hostel (OWNER only)
- `DELETE /api/hostels/{hostelId}` - Delete hostel (OWNER only)

### Room Management
- `POST /api/rooms` - Create room (OWNER only)
- `GET /api/rooms/hostel/{hostelId}` - Get all rooms for a hostel
- `GET /api/rooms/hostel/{hostelId}/available` - Get available rooms for a hostel
- `GET /api/rooms/{roomId}` - Get room by ID
- `PUT /api/rooms/{roomId}` - Update room (OWNER only)
- `PATCH /api/rooms/{roomId}/status` - Update room status (OWNER/WARDEN)
- `DELETE /api/rooms/{roomId}` - Delete room (OWNER only)

## 🚀 Setup & Installation

### Prerequisites
- Java 17 or higher
- Node.js 16 or higher
- MySQL 8 or higher (running on localhost:3306)
- Maven

### Backend Setup

1. Navigate to the backend directory:
```bash
cd hostel-management-backend
```

2. Create a MySQL database user/database if needed. By default, the app uses:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hostel_management?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata
spring.datasource.username=root
spring.datasource.password=
```

3. Build and run the application:
```bash
mvn clean install
mvn spring-boot:run
```

Backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd hostel-management-frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm start
```

Frontend will start on `http://localhost:3000`

## 👥 User Roles & Permissions

### OWNER
- Manage hostels (create, update, delete)
- Add and manage rooms
- View all bookings and payments
- Access all reports

### WARDEN
- Manage bookings
- Handle complaints
- Track visitor check-ins
- Update room status

### STUDENT
- Search and book rooms
- View booking history
- Raise complaints
- Register visitors

### GUARD
- Visitor check-in/check-out
- View visitor records
- Access emergency contacts

## 🎨 UI Features

- Modern, responsive design with Tailwind CSS
- Role-based navigation and quick actions
- Interactive dashboards with statistics
- User-friendly forms with validation
- Real-time error handling and feedback
- Gradient backgrounds and hover effects
- Dropdown menus with user avatars
- Role badges with color coding

## 🔒 Security Features

- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control
- Protected API endpoints
- CORS configuration
- Token expiration handling
- Automatic logout on token expiration

## 📝 Environment Variables

### Backend (application.properties)
```properties
server.port=8080
spring.datasource.url=${MYSQL_URL:jdbc:mysql://localhost:3306/hostel_management?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata}
spring.datasource.username=${MYSQL_USERNAME:root}
spring.datasource.password=${MYSQL_PASSWORD:}
jwt.secret=${JWT_SECRET:your-secret-key-here}
jwt.expiration=${JWT_EXPIRATION:86400000}
```

### Frontend (API base URL)
Update in `src/services/api.js`:
```javascript
const API_BASE_URL = 'http://localhost:8080/api';
```

## 🐛 Known Issues & Limitations

- File upload for images is currently URL-based (direct upload feature pending)
- Room edit page not yet implemented (can be created similar to CreateRoom)
- Hostel edit page not yet implemented (can be created similar to CreateHostel)
- Statistics on dashboard are currently static placeholders

## 📱 Responsive Design

The application is fully responsive and works on:
- Desktop computers
- Tablets
- Mobile devices

## 🤝 Contributing

This is a project under development. Future enhancements include:
- Booking management system
- Payment gateway integration
- Complaint resolution workflow
- Visitor management system
- Mess management features
- Notification system
- Email integration
- File upload for images
- Advanced search and filters
- Analytics dashboard
- Reports export (PDF/Excel)

## 📄 License

This project is for educational purposes.

## 👨‍💻 Development Timeline

- **Phase 1** (Week 1): Authentication & User Profile ✅
- **Week 2**: Hostel & Room Management ✅
- **Week 3**: Booking & Payment System (Upcoming)
- **Week 4**: Complaints, Visitors & Mess Management (Upcoming)
- **Week 5-7**: Advanced Features & Testing

## 📧 Contact

For any queries or suggestions, please reach out to the development team.

---

**Last Updated**: Week 2 - Hostel & Room Management Complete
**Version**: 0.2.0
**Status**: Active Development
