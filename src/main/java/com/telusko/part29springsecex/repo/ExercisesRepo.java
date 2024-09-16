package com.telusko.part29springsecex.repo;

import com.telusko.part29springsecex.model.Exercises;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExercisesRepo extends JpaRepository<Exercises, Long> {
    // You can define custom query methods here if needed
}