package com.rimsha.ContactApp.service;

import com.rimsha.ContactApp.dto.LoginDto;
import com.rimsha.ContactApp.model.User;
import com.rimsha.ContactApp.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public String checkEmailPassword(LoginDto loginDto){
        Optional<User> existingUser = userRepo.findByEmail(loginDto.getEmail());

        if(existingUser.isEmpty()){
            return "User doesn't exist.";
        }

        User currentUser = existingUser.get();

        if(!currentUser.getPassword().equals(loginDto.getPassword())){
            return "password is not correct";
        }
        return "User logged in";
    }
}
