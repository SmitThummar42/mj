package com.love.mj.controller;

import com.love.mj.dto.UniqueCodeDTO;
import com.love.mj.entity.LoveMessages;
import com.love.mj.entity.LoveUser;
import com.love.mj.entity.MessageBucket;
import com.love.mj.repos.LoveUserRepo;
import com.love.mj.repos.MessageBucketRepo;
import com.love.mj.services.UniqueCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/uniquecode")
public class UniqueCodeController {

    @Autowired
    private UniqueCodeService uniqueCodeService;


    // Validate Unique Code for User Role
    @PostMapping("/validatecode")
    public ResponseEntity<String> bucketaccessBasedOnUniqueCode(@RequestParam String username, @RequestBody UniqueCodeDTO uniqueCodeDTO) {

        try {
            return ResponseEntity.status(200).body(uniqueCodeService.bucketaccessBasedOnUniqueCode(username, uniqueCodeDTO));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    //get unique code for ADMIN
    @GetMapping("/getuniquecode")
    public ResponseEntity<String> getUniqueCode(@RequestParam String username) {

        try {
            return ResponseEntity.status(200).body(uniqueCodeService.getUniqueCode(username));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
