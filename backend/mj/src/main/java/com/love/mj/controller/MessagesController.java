package com.love.mj.controller;

import com.love.mj.entity.LoveMessages;
import com.love.mj.entity.LoveUser;
import com.love.mj.entity.MessageBucket;
import com.love.mj.repos.LoveUserRepo;
import com.love.mj.repos.MessageBucketRepo;
import com.love.mj.services.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/message")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @GetMapping("/getallmsg")
    public ResponseEntity<Map<Integer, String>> getallmsg(@RequestParam String username) {
        try {
            return ResponseEntity.status(200).body(messagesService.getAllMsg(username));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Collections.singletonMap(1, e.getMessage()));
        }
    }


    @PostMapping("/addmsg")
    public ResponseEntity<String> addMessage(@RequestParam String username, @RequestBody Map<Integer, String> messageMap) {

        try {
            return ResponseEntity.status(200).body(messagesService.addMessage(username, messageMap));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


}
