package com.rimsha.ContactApp.service;

import com.rimsha.ContactApp.dto.LoginDto;
import com.rimsha.ContactApp.dto.SignUpDto;
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

    public User createUser(User user){
        userRepo.save(user);
        return user;
    }

    public User updateUser(String id, User user){
        User updatedUser = userRepo.findById(id).get();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setPhone(user.getPhone());
        updatedUser.setAddress(user.getAddress());
        userRepo.save(updatedUser);
        return user;
    }

    public SignUpDto checkEmailPassword(LoginDto loginDto) throws Exception {
        Optional<User> existingUser = userRepo.findByEmail(loginDto.getEmail());

        System.out.println("Email provided: " + loginDto.getEmail());
        System.out.println("Password provided: " + loginDto.getPassword());

        if(!existingUser.isPresent()){
            throw new Exception("User doesn't exist.");
        }

        User currentUser = existingUser.get();

        if(!currentUser.getPassword().equals(loginDto.getPassword())){
            throw new Exception("password is not correct");
        }

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setId(currentUser.getId());
        signUpDto.setFirstName(currentUser.getFirstName());
        signUpDto.setLastName(currentUser.getLastName());
        signUpDto.setEmail(currentUser.getEmail());
        signUpDto.setPhone(currentUser.getPhone());
        signUpDto.setAddress(currentUser.getAddress());

        return signUpDto;
    }
}
