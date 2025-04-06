package com.love.mj.controller;

import com.love.mj.dto.LoginDTO;
import com.love.mj.entity.LoveUser;

import com.love.mj.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {

        try {
            return ResponseEntity.status(200).body(authService.login(loginDTO));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }


    }

    // Signup Endpoint
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody LoveUser newUser) {
        try {
            return ResponseEntity.status(200).body(authService.signup(newUser));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


}
