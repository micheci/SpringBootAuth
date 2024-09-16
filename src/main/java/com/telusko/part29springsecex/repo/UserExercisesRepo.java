package com.telusko.part29springsecex.repo;

import com.telusko.part29springsecex.model.UserExercises;
import com.telusko.part29springsecex.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserExercisesRepo extends JpaRepository<UserExercises,Long> {


    List<UserExercises> findByUser(Users user);
}
