package com.bookStore.service;

import com.bookStore.entity.PasswordResetToken;
import com.bookStore.entity.User;
import com.bookStore.repository.PasswordResetTokenRepository;
import com.bookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    public User registerUser(String email, String username, String password, String role) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(email, username, encodedPassword, role);
        return userRepository.save(user);
    }
    
    public User registerOAuth2User(String email, String name, String role) {
        // For OAuth2 users, use email as username and set a dummy password
        String dummyPassword = passwordEncoder.encode("OAUTH2_USER");
        User user = new User(email, email, dummyPassword, role);
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User findByEmail(String email){
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        // Normalize email to lowercase for consistent lookup
        email = email.trim().toLowerCase();
        return userRepository.findByEmail(email);
    }
    
    // Method to find user by username or email (for OAuth2 compatibility)
    public User findByUsernameOrEmail(String identifier) {
        User user = userRepository.findByUsername(identifier);
        if (user == null) {
            user = userRepository.findByEmail(identifier);
        }
        return user;
    }

    public void createPasswordResetTokenForUser(User user, String appUrl) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusHours(1); // 1 hour expiry
        PasswordResetToken resetToken = new PasswordResetToken(token, user, expiry);
        tokenRepository.save(resetToken);
        sendResetEmail(user, token, appUrl);
    }

    public void sendResetEmail(User user, String token, String appUrl) {
        String url = appUrl + "/reset-password?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link below:\n" + url + "\nThis link will expire in 1 hour.");
        mailSender.send(message);
    }

    public PasswordResetToken getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public boolean isTokenValid(PasswordResetToken token) {
        return token != null && token.getExpiryDate().isAfter(LocalDateTime.now());
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        PasswordResetToken token = tokenRepository.findByUser(user);
        if (token != null) tokenRepository.delete(token);
    }
} 