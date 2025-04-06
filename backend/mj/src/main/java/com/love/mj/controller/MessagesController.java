package com.love.mj.controller;

import com.love.mj.entity.LoveMessages;
import com.love.mj.entity.LoveUser;
import com.love.mj.entity.MessageBucket;
import com.love.mj.repos.LoveUserRepo;
import com.love.mj.repos.MessageBucketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/message")
public class MessagesController {

    @Autowired
    private MessageBucketRepo messageBucketRepo;

    @Autowired
    private LoveUserRepo loveUserRepo;

    @GetMapping("/getallmsg")
    public ResponseEntity<Map<Integer, String>> getallmsg(@RequestParam String username){

        Optional<MessageBucket> bucketFromDB= messageBucketRepo.findByUser(loveUserRepo.findByUsername(username).get());

        if (bucketFromDB.isEmpty()) {
            return ResponseEntity.status(409).body(Collections.singletonMap(0, "No message in bucket"));
        }


        Map<Integer, String> lovemessages = bucketFromDB.get().getMessages().stream()
                .collect(Collectors.toMap(LoveMessages::getPriority, LoveMessages::getMessageText));

        return ResponseEntity.status(200).body(lovemessages);

    }


    @PostMapping("/addmsg")
    public ResponseEntity<String> addMessage(@RequestParam String username, @RequestBody Map<Integer, String> messageMap) {
        // Fetch user from database
        LoveUser user = loveUserRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(user.getRole().equals(LoveUser.Role.USER)){
            return ResponseEntity.status(401).body("Only Admin Can Add Msg");
        }
        // Check if bucket exists for the user
        Optional<MessageBucket> bucketFromDB = messageBucketRepo.findByUser(user);

        MessageBucket bucket;
        if (bucketFromDB.isEmpty()) {
            // Create a new bucket if none exists
            bucket = new MessageBucket();
            bucket.setUsers((List<LoveUser>) user);
            bucket.setMessages(new ArrayList<>());
            messageBucketRepo.save(bucket); // Save the new bucket to the database
        } else {
            return ResponseEntity.status(200).body("Messages Already present");
        }

        // Iterate over the map to create and add LoveMessages to the bucket
        messageMap.forEach((priority, messageText) -> {
            LoveMessages newMessage = new LoveMessages();
            newMessage.setPriority(priority);
            newMessage.setMessageText(messageText);
            newMessage.setBucket(bucket); // Associate message with the bucket
            bucket.getMessages().add(newMessage);
        });

        // Save the updated bucket
        messageBucketRepo.save(bucket);

        return ResponseEntity.status(200).body("Messages added successfully to the bucket");
    }




}
