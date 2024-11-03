package com.rimsha.ContactApp.controller;

import com.rimsha.ContactApp.dto.ChangePasswordDto;
import com.rimsha.ContactApp.dto.LoginDto;
import com.rimsha.ContactApp.dto.SignUpDto;
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
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @PostMapping("/login")
    public SignUpDto checkEmailPassword(@RequestBody LoginDto loginDto) throws Exception {
        return userService.checkEmailPassword(loginDto);
    }

    @PutMapping("/changePassword/{id}")
    public ChangePasswordDto changePassword(@PathVariable String id, @RequestBody ChangePasswordDto changePasswordDto) {
        return userService.changePassword(id, changePasswordDto);
    }

}
