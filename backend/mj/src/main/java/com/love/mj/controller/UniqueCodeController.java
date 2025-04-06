package com.love.mj.controller;

import com.love.mj.dto.UniqueCodeDTO;
import com.love.mj.entity.LoveUser;
import com.love.mj.entity.MessageBucket;
import com.love.mj.repos.LoveUserRepo;
import com.love.mj.repos.MessageBucketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

        Optional<MessageBucket> bucketOptional=messageBucketRepo.findByUser(adminUser.get());

        Optional<LoveUser> user= userRepository.findByUsername(username);

        MessageBucket messageBucket= new MessageBucket();

        messageBucket.setMessages(bucketOptional.get().getMessages());

        List<LoveUser> loveUsers=bucketOptional.get().getUsers();
        loveUsers.add(user.get());

        messageBucket.setUsers(loveUsers);

        messageBucketRepo.save(messageBucket);

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
