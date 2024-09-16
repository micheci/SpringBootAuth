package com.telusko.part29springsecex.service;

import com.telusko.part29springsecex.model.UserExercises;
import com.telusko.part29springsecex.model.Users;
import com.telusko.part29springsecex.repo.UserExercisesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserExerciseService {

    @Autowired
    private UserExercisesRepo userExerciseRepo;

    public UserExercises save(UserExercises userExercises){
        return userExerciseRepo.save(userExercises);
    }

    // function to get the row of a userExercise
    public UserExercises findById(long id){
        return userExerciseRepo.findById(id).orElse(null);
    }

    // fetch all exercises for a user
    public List<UserExercises> findByUser(Users user) {
        return userExerciseRepo.findByUser(user);
    }


}
