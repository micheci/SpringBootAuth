package com.telusko.part29springsecex.controller;

import com.telusko.part29springsecex.model.UserWeight;
import com.telusko.part29springsecex.model.Users;
import com.telusko.part29springsecex.service.JWTService;
import com.telusko.part29springsecex.service.UserService;
import com.telusko.part29springsecex.service.UserWeightService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/weight")
public class UserWeightController {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserWeightService UserWeightService;

    @PostMapping("/add")
    public String addWeight(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                            @RequestBody UserWeight userWeight) {
        // Extract JWT token from the Authorization header
        String token = authHeader.replace("Bearer ", "");

        // Extract claims from the token
        Claims claims = jwtService.extractAllClaims(token);
        String username = claims.getSubject();

        if (username == null) {
            return "Token does not contain username";
        }

        // Find user by username
        Users user = userService.findByUsername(username);
        if (user == null) {
            return "User not found";
        }

        // Set the user and entry date
        userWeight.setUser(user);
        userWeight.setEntryDate(LocalDateTime.now());

        // Save the weight entry
        UserWeightService.save(userWeight);

        return "Weight entry added successfully";
    }

}
