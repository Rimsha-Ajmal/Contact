package com.rimsha.ContactApp.controller;

import com.rimsha.ContactApp.model.User;
import com.rimsha.ContactApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping
    public String createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable String id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

}
