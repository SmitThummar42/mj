package com.love.mj.services;

import com.love.mj.dto.UniqueCodeDTO;
import com.love.mj.entity.LoveUser;
import com.love.mj.entity.MessageBucket;
import com.love.mj.repos.LoveUserRepo;
import com.love.mj.repos.MessageBucketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UniqueCodeService {

    @Autowired
    private LoveUserRepo userRepository;

    @Autowired
    private MessageBucketRepo messageBucketRepo;


    public String bucketaccessBasedOnUniqueCode(String username, UniqueCodeDTO uniqueCodeDTO) {

        //user who gave unique code
        Optional<LoveUser> adminUser = userRepository.findByUniqueCode(uniqueCodeDTO.getUniqueCode());

        if (adminUser.isEmpty()) {
            throw new IllegalArgumentException("Unique Code is wrong");
        }

        List<MessageBucket> buckets = messageBucketRepo.findAllByUsersContaining(adminUser.get());

        if (buckets.isEmpty()) {
            throw new IllegalArgumentException("No message buckets found for admin user");
        }

        Optional<LoveUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
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

        return "Unique Code is validated";
    }

    public String getUniqueCode(String username) {

        Optional<LoveUser> adminUser = userRepository.findByUsername(username);

        if (adminUser.isEmpty()) {
            throw new IllegalArgumentException("User Not found");
        }

        return adminUser.get().getUniqueCode();
    }
}
