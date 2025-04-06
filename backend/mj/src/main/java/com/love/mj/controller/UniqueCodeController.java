package com.love.mj.controller;

import com.love.mj.dto.UniqueCodeDTO;
import com.love.mj.entity.LoveMessages;
import com.love.mj.entity.LoveUser;
import com.love.mj.entity.MessageBucket;
import com.love.mj.repos.LoveUserRepo;
import com.love.mj.repos.MessageBucketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/uniquecode")
public class UniqueCodeController
{

    @Autowired
    private LoveUserRepo userRepository;

    @Autowired
    private MessageBucketRepo messageBucketRepo;

    // Validate Unique Code for User Role
    @PostMapping("/validatecode")
    public ResponseEntity<String> bucketaccessBasedOnUniqueCode(@RequestParam String username, @RequestBody UniqueCodeDTO uniqueCodeDTO){

        //user who gave unique code
        Optional<LoveUser> adminUser= userRepository.findByUniqueCode(uniqueCodeDTO.getUniqueCode());

        if (adminUser.isEmpty()){
            return ResponseEntity.status(400).body("Unique Code is wrong");
        }

        List<MessageBucket> buckets = messageBucketRepo.findAllByUsersContaining(adminUser.get());
        
        if (buckets.isEmpty()) {
            return ResponseEntity.status(400).body("No message buckets found for admin user");
        }

        Optional<LoveUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.status(400).body("User not found");
        }

        // Add user to all buckets the admin has access to
        for (MessageBucket bucket : buckets) {
            List<LoveUser> users = new ArrayList<>(bucket.getUsers());
            if (!users.contains(user.get())) {
                users.add(user.get());
                bucket.setUsers(users);
                messageBucketRepo.save(bucket);
            }
        }

        return ResponseEntity.status(200).body("Unique Code is validated");


    }

    //get unique code for ADMIN

    @GetMapping("/getuniquecode")
    public ResponseEntity<String> getUniqueCode(@RequestParam String username){
        Optional<LoveUser> adminUser= userRepository.findByUsername(username);

        if (adminUser.isEmpty()){
            return ResponseEntity.status(400).body("User Not found");
        }

        return ResponseEntity.status(200).body(adminUser.get().getUniqueCode());
    }
}
