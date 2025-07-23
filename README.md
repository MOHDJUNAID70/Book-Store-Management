# 📚 Book Store Management System

A modern, full-featured bookstore management system built with Spring Boot, featuring a beautiful UI, secure authentication, and complete e-commerce functionality.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3.0-purple)
![License](https://img.shields.io/badge/License-MIT-blue)

## 🌟 Features

### 🔐 Authentication & Security
- **Dual Authentication**: Traditional username/password and Google OAuth2 integration
- **Role-Based Access Control**: Admin and User roles with different permissions
- **Secure Password Reset**: Email-based password reset with token validation
- **Session Management**: Secure session handling with Spring Security

### 📖 Book Management
- **Admin Features**: Add, edit, and manage book inventory
- **Book Catalog**: Browse books with advanced filtering and search
- **Image Upload**: Support for book cover images
- **Category Management**: Organize books by categories

### 🛒 Shopping Experience
- **Personal Library**: Users can add books to their personal collection
- **Selective Purchase**: Choose specific books to buy with checkboxes
- **Dynamic Pricing**: Real-time price calculation for selected items
- **Shopping Cart**: Full shopping cart functionality

### 💳 Payment Gateway
- **Multiple Payment Methods**: Credit/Debit cards, UPI, and Net Banking
- **Secure Processing**: Simulated secure payment processing
- **Order Summary**: Detailed order breakdown before payment
- **Transaction Management**: Complete purchase flow

### 🎨 Modern UI/UX
- **Professional Design**: Purple gradient theme with glass morphism effects
- **Responsive Layout**: Mobile-first design that works on all devices
- **Interactive Elements**: Smooth animations and hover effects
- **Consistent Branding**: Unified design language across all pages

## 🛠️ Technology Stack

### Backend
- **Java 17**: Latest LTS version of Java
- **Spring Boot 3.5.0**: Modern Spring framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Database operations
- **Thymeleaf**: Server-side templating engine
- **MySQL/H2**: Database support

### Frontend
- **Bootstrap 5.3.0**: Responsive CSS framework
- **FontAwesome**: Icon library
- **Inter Font**: Modern typography
- **Custom CSS**: Glass morphism and gradient effects
- **JavaScript**: Dynamic interactions and form validation

### Security & OAuth
- **Google OAuth2**: Social login integration
- **BCrypt**: Password encryption
- **JWT**: Token-based authentication
- **CSRF Protection**: Cross-site request forgery protection

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (or use H2 for development)
- Gmail account for email functionality

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/bookstore-management.git
   cd bookstore-management
   ```

2. **Configure Database**
   ```properties
   # application.properties
   spring.datasource.url=jdbc:mysql://localhost:3306/bookstore
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Configure Google OAuth2**
   ```properties
   # Google OAuth2 Configuration
   spring.security.oauth2.client.registration.google.client-id=your-google-client-id
   spring.security.oauth2.client.registration.google.client-secret=your-google-client-secret
   ```

4. **Configure Email Settings**
   ```properties
   # Email Configuration
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-app-password
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

6. **Access the application**
   - Open your browser and navigate to `http://localhost:8080`
   - Register a new account or login with Google

## 📱 Application Screenshots

### Home Dashboard
Modern dashboard with gradient background and glass morphism cards displaying user statistics and quick actions.

### Available Books
Professional book catalog with advanced filtering, search functionality, and responsive card layout.

### My Books & Shopping Cart
Personal book collection with selective purchase options and real-time price calculation.

### Payment Gateway
Secure payment interface supporting multiple payment methods with professional styling.

### Authentication Pages
Modern login and registration pages with Google OAuth2 integration and forgot password functionality.

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/bookStore/
│   │   ├── config/          # Security and configuration
│   │   ├── controller/      # REST controllers
│   │   ├── entity/          # JPA entities
│   │   ├── repository/      # Data repositories
│   │   ├── service/         # Business logic
│   │   └── BookStoreApplication.java
│   └── resources/
│       ├── templates/       # Thymeleaf templates
│       ├── static/          # CSS, JS, images
│       └── application.properties
```

## 🔧 Configuration

### Database Configuration
The application supports both MySQL and H2 databases. For development, H2 is configured by default.

### Security Configuration
- CSRF protection enabled
- OAuth2 integration with Google
- Role-based access control
- Secure password encoding

### Email Configuration
Configure SMTP settings for password reset functionality:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## 👥 User Roles

### Admin Users
- Add new books to the catalog
- Manage book inventory
- View all user activities
- Access admin dashboard

### Regular Users
- Browse book catalog
- Add books to personal library
- Purchase selected books
- Manage account settings

## 🔒 Security Features

- **Password Encryption**: BCrypt hashing for secure password storage
- **OAuth2 Integration**: Secure Google login with proper token handling
- **CSRF Protection**: Protection against cross-site request forgery
- **Input Validation**: Server-side validation for all user inputs
- **Session Security**: Secure session management with timeout

## 🎨 UI/UX Features

- **Glass Morphism**: Modern glass-like effects with backdrop blur
- **Gradient Themes**: Beautiful purple gradient backgrounds
- **Responsive Design**: Mobile-first approach with Bootstrap
- **Interactive Elements**: Smooth animations and hover effects
- **Professional Typography**: Inter font for clean, modern text
- **Consistent Branding**: Unified color scheme and design language

## 📧 Email Features

- **Welcome Emails**: Automated welcome emails for new users
- **Password Reset**: Secure password reset with time-limited tokens
- **Order Confirmations**: Email confirmations for purchases
- **HTML Templates**: Professional email templates

## 🛡️ Error Handling

- **Global Exception Handling**: Centralized error handling
- **User-Friendly Messages**: Clear error messages for users
- **Logging**: Comprehensive logging for debugging
- **Validation**: Client and server-side validation

## 🚀 Deployment

### Local Development
```bash
mvn spring-boot:run
```

### Production Deployment
1. Build the JAR file:
   ```bash
   mvn clean package
   ```

2. Run with production profile:
   ```bash
   java -jar target/bookstore-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
   ```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)
- LinkedIn: [Your LinkedIn](https://linkedin.com/in/yourprofile)
- Email: your.email@example.com

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Bootstrap team for the responsive CSS framework
- FontAwesome for the beautiful icons
- Google for OAuth2 integration
- All contributors who helped improve this project

## 📞 Support

If you have any questions or need help with setup, please:
1. Check the [Issues](https://github.com/yourusername/bookstore-management/issues) page
2. Create a new issue if your problem isn't already listed
3. Contact the maintainer directly

---

⭐ **If you found this project helpful, please give it a star!** ⭐
