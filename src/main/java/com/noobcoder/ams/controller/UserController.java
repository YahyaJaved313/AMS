package com.noobcoder.ams.controller;

import com.noobcoder.ams.model.User;
import com.noobcoder.ams.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DuplicateKeyException;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Check if email already exists
        if (userService.findByEmail(user.getEmail()) != null) {
            throw new DuplicateKeyException("Email " + user.getEmail() + " already exists");
        }
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }
    @GetMapping("/health")
    public String healthcheck(){
        return "I am healthy";
    }
}