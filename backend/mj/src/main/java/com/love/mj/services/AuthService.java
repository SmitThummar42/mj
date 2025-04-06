package com.love.mj.services;

import com.love.mj.dto.LoginDTO;
import com.love.mj.entity.LoveUser;
import com.love.mj.repos.LoveUserRepo;
import com.love.mj.repos.MessageBucketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private LoveUserRepo userRepository;

    @Autowired
    private MessageBucketRepo messageBucketRepo;

    public String login(LoginDTO loginDTO) {
        LoveUser user = userRepository.findByUsername(loginDTO.getUsername()).orElse(null);

        if (user == null || !user.getPassword().equals(loginDTO.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return "Login successful! Welcome, " + user.getUsername();
    }

    public String signup(LoveUser newUser) {

        // Check if username already exists
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Save the new user
        userRepository.save(newUser);

        return "Signup successful! You can now log in.";
    }
}
