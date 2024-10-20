package com.rimsha.ContactApp.service;

import com.rimsha.ContactApp.model.User;
import com.rimsha.ContactApp.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<User> getUsers(){
        return userRepo.findAll();
    }

    public String createUser(User user){
        userRepo.save(user);
        return "New user created";
    }

    public String updateUser(String id, User user){
        User updatedUser = userRepo.findById(id).get();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setPhone(user.getPhone());
        updatedUser.setAddress(user.getAddress());
        userRepo.save(updatedUser);
        return  "User updated with user id: " + id;
    }
}
