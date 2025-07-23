package com.bookStore.controller;

import com.bookStore.entity.User;
import com.bookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import com.bookStore.entity.PasswordResetToken;
import org.springframework.beans.factory.annotation.Value;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }
        userService.registerUser(user.getEmail(),user.getUsername(), user.getPassword(), "USER");
        model.addAttribute("success", "Registration successful! Please login.");
        return "login";
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        return "login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgotPassword";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, HttpServletRequest request, Model model) {
        // Trim and validate email format
        email = email.trim().toLowerCase();
        
        // Basic email format validation
        if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            model.addAttribute("error", "Please enter a valid email address.");
            model.addAttribute("email", email);
            return "forgotPassword";
        }
        
        // Check if user exists in database
        User user = userService.findByEmail(email);
        if (user == null) {
            // Also check by username in case they entered username instead of email
            user = userService.findByUsername(email);
        }
        
        if (user == null) {
            model.addAttribute("error", "No account found with that email address. Please check your email and try again.");
            model.addAttribute("email", email);
            return "forgotPassword";
        }
        
        try {
            String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
            userService.createPasswordResetTokenForUser(user, appUrl);
            model.addAttribute("success", "A password reset link has been sent to " + user.getEmail() + ". Please check your email.");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to send reset email. Please try again later.");
            model.addAttribute("email", email);
            return "forgotPassword";
        }
        
        return "forgotPassword";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        PasswordResetToken resetToken = userService.getToken(token);
        if (resetToken == null || !userService.isTokenValid(resetToken)) {
            model.addAttribute("error", "Invalid or expired password reset token.");
            return "resetPassword";
        }
        model.addAttribute("token", token);
        return "resetPassword";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token, @RequestParam("password") String password, Model model) {
        PasswordResetToken resetToken = userService.getToken(token);
        if (resetToken == null || !userService.isTokenValid(resetToken)) {
            model.addAttribute("error", "Invalid or expired password reset token.");
            return "resetPassword";
        }
        userService.updatePassword(resetToken.getUser(), password);
        model.addAttribute("success", "Your password has been reset. You can now log in.");
        return "login";
    }
} 