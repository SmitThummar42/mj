package com.love.mj.services;

import com.love.mj.entity.LoveMessages;
import com.love.mj.entity.LoveUser;
import com.love.mj.entity.MessageBucket;
import com.love.mj.repos.LoveUserRepo;
import com.love.mj.repos.MessageBucketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessagesService {

    @Autowired
    private MessageBucketRepo messageBucketRepo;

    @Autowired
    private LoveUserRepo loveUserRepo;

    public Map<Integer, String> getAllMsg(String username) {

        LoveUser user = loveUserRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<MessageBucket> buckets = messageBucketRepo.findAllByUsersContaining(user);

        if (buckets.isEmpty()) {
            throw new IllegalArgumentException("No message in bucket");
        }

        // Combine messages from all buckets the user has access to
        Map<Integer, String> allMessages = new HashMap<>();
        for (MessageBucket bucket : buckets) {
            bucket.getMessages().forEach(msg ->
                    allMessages.put(msg.getPriority(), msg.getMessageText()));
        }

        return allMessages;
    }

    public String addMessage(String username, Map<Integer, String> messageMap) {

        // Fetch user from database
        LoveUser user = loveUserRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getRole().equals(LoveUser.Role.USER)) {
            throw new IllegalArgumentException("Only Admin Can Add Msg");
        }

        // Check if bucket exists for the user
        List<MessageBucket> bucketFromDB = messageBucketRepo.findAllByUsersContaining(user);

        MessageBucket bucket;
        if (bucketFromDB.isEmpty()) {

            // Create a new bucket if none exists
            bucket = new MessageBucket();
            bucket.setUsers(new ArrayList<>(Collections.singletonList(user)));
            bucket.setMessages(new ArrayList<>()); // New list instance

            messageBucketRepo.save(bucket); // Save the new bucket to the database
        } else {
            return "Messages Already present";
        }

        // Iterate over the map to create and add LoveMessages to the bucket

        messageMap.forEach((priority, messageText) -> {
            LoveMessages newMessage = new LoveMessages();
            newMessage.setPriority(priority);
            newMessage.setMessageText(messageText);
            newMessage.setBucket(bucket); // Associate message with the bucket

            // Create new collections to avoid shared references
            List<LoveMessages> messages = new ArrayList<>(bucket.getMessages());
            messages.add(newMessage);

            bucket.setMessages(messages);
        });

        // Save the updated bucket
        messageBucketRepo.save(bucket);

        return "Messages added successfully to the bucket";
    }
}
