package com.bookStore.service;

import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.bookStore.entity.User;
import com.bookStore.service.UserService;
import com.bookStore.service.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.*;

@Service("customOAuth2UserService")
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        
        User user = userService.findByEmail(email);
        if (user == null) {
            // Register new user with email as username for OAuth2 users
            user = userService.registerOAuth2User(email, name, "USER");
        }
        
        // Return a custom OAuth2User that includes our User entity
        return new CustomOAuth2User(oauth2User, user);
    }
    
    // Custom OAuth2User implementation
    public static class CustomOAuth2User implements OAuth2User {
        private OAuth2User oauth2User;
        private User user;
        
        public CustomOAuth2User(OAuth2User oauth2User, User user) {
            this.oauth2User = oauth2User;
            this.user = user;
        }
        
        @Override
        public Map<String, Object> getAttributes() {
            return oauth2User.getAttributes();
        }
        
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        }
        
        @Override
        public String getName() {
            return user.getEmail(); // Return email as the name for OAuth2 users
        }
        
        public User getUser() {
            return user;
        }
    }
}