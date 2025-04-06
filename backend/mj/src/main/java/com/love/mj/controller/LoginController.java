package com.love.mj.controller;

import com.love.mj.dto.LoginDTO;
import com.love.mj.dto.UniqueCodeDTO;
import com.love.mj.entity.BucketAccess;
import com.love.mj.entity.LoveUser;

import com.love.mj.entity.MessageBucket;
import com.love.mj.repos.LoveUserRepo;
import com.love.mj.repos.MessageBucketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoveUserRepo userRepository;

    @Autowired
    private MessageBucketRepo messageBucketRepo;

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        LoveUser user = userRepository.findByUsername(loginDTO.getUsername()).orElse(null);

        if (user == null || !user.getPassword().equals(loginDTO.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        return ResponseEntity.ok("Login successful! Welcome, " + user.getUsername());
    }

    // Signup Endpoint
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody LoveUser newUser) {
        // Check if username already exists
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body("Username already exists");
        }

        // Save the new user
        userRepository.save(newUser);

        return ResponseEntity.ok("Signup successful! You can now log in.");
    }


}
