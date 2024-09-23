package com.telusko.part29springsecex.controller;

import com.telusko.part29springsecex.model.Exercises;
import com.telusko.part29springsecex.model.UserExercises;
import com.telusko.part29springsecex.model.Users;
import com.telusko.part29springsecex.repo.ExercisesRepo;
import com.telusko.part29springsecex.service.JWTService;
import com.telusko.part29springsecex.service.UserExerciseService;
import com.telusko.part29springsecex.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserExerciseController {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserExerciseService userExercisesService;

    @Autowired
    private ExercisesRepo exercisesRepo;
    // Updating user Exercise
    @PutMapping("/updateUserExercise")
    public String updateExercise(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                 @RequestBody UserExercises updatedUserExercises){
        // Extract JWT token from the Authorization header
        String token = authHeader.replace("Bearer ", "");

        // Extract claims from the token
        Claims claims = jwtService.extractAllClaims(token);
        String username = claims.getSubject();

        if (username == null) {
            return "Token does not contain username";
        }
        // Find user from the database
        Users user = userService.findByUsername(username);
        if (user == null) {
            return "User not found";
        }

        // Find the existing UserExercises record by ID
        UserExercises existingUserExercises = userExercisesService.findById(updatedUserExercises.getId());
        if (existingUserExercises == null) {
            return "UserExercises record not found";
        }
        // Update fields
        existingUserExercises.setReps(updatedUserExercises.getReps());
        existingUserExercises.setSets(updatedUserExercises.getSets());
        existingUserExercises.setWeight(updatedUserExercises.getWeight());
        existingUserExercises.setNotes(updatedUserExercises.getNotes());

        // Save updated record
        userExercisesService.save(existingUserExercises);

        return "Exercise updated successfully";
    }

    //get all users exercises
    @GetMapping("/user/exercises")
    public ResponseEntity<?> getUserExercises(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        // Extract JWT token from the Authorization header
        String token = authHeader.replace("Bearer ", "");

        // Extract claims from the token
        Claims claims = jwtService.extractAllClaims(token);
        String username = claims.getSubject();

        if (username == null) {
            return ResponseEntity.badRequest().body("Token does not contain username");
        }

        // Find user by username
        Users user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        // Fetch the user's exercises
        List<UserExercises> exercises = userExercisesService.findByUser(user);

        if (exercises.isEmpty()) {
            return ResponseEntity.status(404).body("No exercises found for user");
        }

        return ResponseEntity.ok(exercises);
    }
    // Adding user exercise
    @PostMapping("/addExercise")
    public String addExercise(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                              @RequestBody UserExercises userExercises) {
        // Extract JWT token from the Authorization header
        String token = authHeader.replace("Bearer ", "");

        // Extract claims from the token
        Claims claims = jwtService.extractAllClaims(token);
        String username = claims.getSubject();

        if (username == null) {
            return "Token does not contain username";
        }
        // Find user from the database
        Users user = userService.findByUsername(username);
        if (user == null) {
            return "User not found";
        }

        // Set the user in the UserExercises entity
        userExercises.setUser(user);

        // Fetch the Exercise entity from the database using the provided exercise ID
        Exercises exercise = exercisesRepo.findById(userExercises.getExercise().getExerciseId()).orElse(null);
        if (exercise == null) {
            return "Exercise not found";
        }
        System.out.println("Exercise:"+exercise);

        // Set the exercise in the UserExercises entity
        userExercises.setExercise(exercise);

        // Save the UserExercises entity to the database
        userExercisesService.save(userExercises);

        return "Exercise added successfully";
    }

    // Get all available exercises
    @GetMapping("/exercises")
    public ResponseEntity<?> getAllExercises() {
        List<Exercises> exercises = exercisesRepo.findAll(); // Fetch all exercises

        if (exercises.isEmpty()) {
            return ResponseEntity.status(404).body("No exercises found");
        }

        return ResponseEntity.ok(exercises);
    }

}
