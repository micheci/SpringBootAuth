package com.telusko.part29springsecex.repo;

import com.telusko.part29springsecex.model.UserWeight;
import com.telusko.part29springsecex.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWeightRepo extends JpaRepository<UserWeight,Long> {
    List<UserWeight> findByUserOrderByEntryDateDesc(Users user);
}
