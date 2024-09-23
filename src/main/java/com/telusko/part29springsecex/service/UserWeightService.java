package com.telusko.part29springsecex.service;

import com.telusko.part29springsecex.model.UserWeight;
import com.telusko.part29springsecex.model.Users;
import com.telusko.part29springsecex.repo.UserWeightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWeightService {

    @Autowired
    private UserWeightRepo userWeightRepo;

    public UserWeight save(UserWeight userWeight){
        return userWeightRepo.save(userWeight);
    }

    public List<UserWeight> findByUser(Users user) {
        return userWeightRepo.findByUserOrderByEntryDateDesc(user);
    }
}
