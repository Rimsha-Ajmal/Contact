package com.rimsha.ContactApp.controller;

import com.rimsha.ContactApp.dto.ChangePasswordDto;
import com.rimsha.ContactApp.dto.LoginDto;
import com.rimsha.ContactApp.dto.SignUpDto;
import com.rimsha.ContactApp.exceptionHandling.ResourceNotFoundException;
import com.rimsha.ContactApp.model.User;
import com.rimsha.ContactApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        List<User> users = userService.getUsers();
        if(users == null || users.isEmpty()){
            throw new ResourceNotFoundException(("User not found"));
        }
        return users;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (user == null || user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPhone() == null || user.getAddress() == null) {
            throw new ResourceNotFoundException("User creation failed. Ensure none of the field is empty.");
        }
        else if (user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getEmail().isEmpty() || user.getPhone().isEmpty() || user.getAddress().isEmpty()) {
            throw new ResourceNotFoundException("User creation failed. Ensure none of the field is empty.");
        }
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
//        if (user == null || id == null || id.isEmpty()) {
//            throw new ResourceNotFoundException("User data is missing.");
//        }
        if (!userService.getCurrentUser(id).isPresent() || userService.getCurrentUser(id).isEmpty()) {
            throw new ResourceNotFoundException("User not found.");
        }
        else if (user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getEmail().isEmpty() || user.getPhone().isEmpty() || user.getAddress().isEmpty()) {
            throw new ResourceNotFoundException("User updation failed. Ensure none of the field is empty.");
        }
        return userService.updateUser(id, user);
    }

    @PostMapping("/login")
    public SignUpDto checkEmailPassword(@RequestBody LoginDto loginDto) throws Exception {
        if(loginDto == null || loginDto.getEmail() == null || loginDto.getPassword() == null){
            throw new ResourceNotFoundException("User login failed. Ensure none of the field is empty.");
        }
        else if(loginDto.getEmail().isEmpty() || loginDto.getPassword().isEmpty()){
            throw new ResourceNotFoundException("User login failed. Ensure none of the field is empty.");
        }
        return userService.checkEmailPassword(loginDto);
    }

    @PutMapping("/changePassword/{id}")
    public ChangePasswordDto changePassword(@PathVariable String id, @RequestBody ChangePasswordDto changePasswordDto) {
        if(id == null || id.isEmpty()){
            throw new ResourceNotFoundException("User ID is missing.");
        }
        else if(!userService.getCurrentUser(id).isPresent()){
            throw new ResourceNotFoundException("User not found.");
        }
        else if(changePasswordDto == null || changePasswordDto.getCurrentPassword() == null || changePasswordDto.getNewPassword() == null){
            throw new ResourceNotFoundException("User password updation failed. Ensure none of the field is empty.");
        }
        else if(changePasswordDto.getCurrentPassword().isEmpty() || changePasswordDto.getNewPassword().isEmpty()){
            throw new ResourceNotFoundException("User password updation failed. Ensure none of the field is empty.");
        }
        return userService.changePassword(id, changePasswordDto);
    }

}
