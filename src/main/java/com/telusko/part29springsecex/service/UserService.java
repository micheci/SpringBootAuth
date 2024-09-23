package com.telusko.part29springsecex.service;

import com.telusko.part29springsecex.dto.LoginResponse;
import com.telusko.part29springsecex.dto.UserResponse;
import com.telusko.part29springsecex.model.Users;
import com.telusko.part29springsecex.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepo repo;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return user;
    }

    public LoginResponse verify(Users user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(user.getUsername());
            Users loggedInUser = findByUsername(user.getUsername());
            UserResponse userResponse = new UserResponse(loggedInUser.getId(), loggedInUser.getUsername());
            return new LoginResponse(token, userResponse);
        } else {
            throw new RuntimeException("Authentication failed");
        }
    }



    public Users findById(Long userId) {
        Optional<Users> userOptional = repo.findById(Math.toIntExact(userId));
        return userOptional.orElse(null); // Return null if the user is not found
    }

    public Users findByUsername(String username) {
        // Call the repository method to find user by username
        return repo.findByUsername(username);
    }
}
